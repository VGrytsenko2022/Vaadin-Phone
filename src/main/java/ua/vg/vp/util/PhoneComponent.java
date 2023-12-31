/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.vg.vp.util;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import ua.vg.vp.Application;


/**
 * @author vgvadmin
 */
@Tag("audio")
@JsModule("./js/jssip-3.10.0.js")
@JsModule("./js/phone.js")
public class PhoneComponent extends com.vaadin.flow.component.Component {

    public PhoneComponent() {
        setId("localAudio");
        getElement().setAttribute("autoplay", "true");
        hasGetUserMedia();
        setProperty(Application.configuration.getWebrtcUser(), Application.configuration.getWebrtcPassword(), Application.configuration.getDomain());
        activate();
    }

    public void hasGetUserMedia(){
        getElement().executeJs("hasGetUserMedia()");
    }

    private void setProperty(String webrtcUser, String webrtcPassword, String domain) {
        getElement().executeJs("setProperty($0,$1,$2,$3,$4)", webrtcUser, webrtcPassword, domain, "wss://" + domain + ":8089/asterisk/ws",this);
    }
    public void activate(){
        getElement().executeJs("configureAndActivate()");
    }

    public void answer(){
       getElement().executeJs("answer()");
    }

    public void endCall(){
       getElement().executeJs("endCall()");
    }

    public void muteAudio(){
       getElement().executeJs("muteAudio()");
    }

    public void muteVideo(){
        getElement().executeJs("muteVideo()");
    }

    public void localHold(){
       getElement().executeJs("localHold()");
    }

    public void callSendDTMF(String symbol) {
       getElement().executeJs("callSendDTMF($0)", symbol);
    }

    public void makeCall(String dialNum) {
       getElement().executeJs("makeCall($0)", dialNum);
    }

   // @ClientCallable
  //  public void auEvent(EventType eventType,String event) {
  //      fireEvent(new ChangeEvent(this, false, eventType, event));
  //  }

    @DomEvent("phone")
    public static class ChangeEvent extends ComponentEvent<PhoneComponent> {
        private final String event;
        private final String eventType;
        public ChangeEvent(PhoneComponent source, boolean fromClient, @EventData("event.detail.type") String eventType, @EventData("event.detail.phoneEvent") String event) {
            super(source, fromClient);
            this.event = event;
            this.eventType=eventType;
        }

        public String getEventValue() {
            return event;
        }
        public EventType getEventType() {
            return EventType.valueOf(eventType);
        }
    }

    public void addValueChangeListener(ComponentEventListener<ChangeEvent> listener) {
        addListener(ChangeEvent.class, listener);
    }
}
