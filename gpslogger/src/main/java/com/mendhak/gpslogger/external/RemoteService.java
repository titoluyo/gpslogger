package com.mendhak.gpslogger.external;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import com.mendhak.gpslogger.common.Session;
import com.mendhak.gpslogger.common.events.CommandEvents;

import org.slf4j.LoggerFactory;

import de.greenrobot.event.EventBus;

public class RemoteService extends Service {

    private Messenger messenger;
    private org.slf4j.Logger tracer;

    @Override
    public void onCreate(){
        tracer = LoggerFactory.getLogger(RemoteService.class.getSimpleName());
    }

    @Override
    public IBinder onBind(Intent intent) {
        if(this.messenger == null){
            synchronized (RemoteService.class){
                if(this.messenger == null){
                    this.messenger = new Messenger(new IncomingHandler());
                }
            }
        }
        return this.messenger.getBinder();
    }

    private class IncomingHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            tracer.debug("*****************************************");
            tracer.debug("Remote Service successfully invoked!!!!!!");
            tracer.debug("*****************************************");

            tracer.debug("Session.isStarted:" + Session.isStarted());
            if(!Session.isStarted()) {
                //EventBus.getDefault().post(new CommandEvents.RequestToggle());
                EventBus.getDefault().post(new CommandEvents.RequestToggle());
            }
            tracer.debug("Session.isStarted:" + Session.isStarted());

            int what = msg.what;
            //Toast.makeText(RemoteService.this.getApplicationContext(), "Remote Service invoked-("+what+")", Toast.LENGTH_LONG).show();

            Bundle bundleReceived = msg.getData();
            String annotation = bundleReceived.getString("annotate");
            if(annotation != null){
                EventBus.getDefault().post(new CommandEvents.Annotate(annotation));
            }

            //Setup the reply message
            Message message = Message.obtain(null, 2, 0, 0);

            Bundle bundle = new Bundle();
            String datos = "estos son los datos nuevos";
            bundle.putString("mensaje", datos);
            message.setData(bundle);

            try
            {
                //make the RPC invocation
                Messenger replyTo = msg.replyTo;
                replyTo.send(message);
            }
            catch(RemoteException rme)
            {
                //Show an Error Message
                Toast.makeText(RemoteService.this, "Invocation Failed!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
