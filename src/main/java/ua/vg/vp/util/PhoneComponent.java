/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.vg.vp.util;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;


/**
 * @author vgvadmin
 */
@Tag("audio")
@JsModule("./js/jssip-3.10.0.js")
@JsModule("./js/phone.js")
//@Route("phone")
public class PhoneComponent extends com.vaadin.flow.component.Component {

    public PhoneComponent() {
        setId("localAudio");
        getElement().setAttribute("autoplay", "true");
        setProperty("199", "123456", "vgbc.pp.ua");
        activate();
    }
    public PhoneComponent(String webrtcUser, String webrtcPassword, String domain) {
        setProperty(webrtcUser,webrtcPassword,domain);
        activate();
    }


    private void setProperty(String webrtcUser, String webrtcPassword, String domain) {
        UI.getCurrent().getPage().executeJs("setProperty($0,$1,$2,$3,$4)", webrtcUser, webrtcPassword, domain, "wss://" + domain + ":8089/asterisk/ws",this);
    }
    public void activate(){
        UI.getCurrent().getPage().executeJs("configureAndActivate()");
    }

    public void answer(){
        UI.getCurrent().getPage().executeJs("answer()");
    }

    public void endCall(){
        UI.getCurrent().getPage().executeJs("endCall()");
    }

    public void muteAudio(){
        UI.getCurrent().getPage().executeJs("muteAudio()");
    }

    public void muteVideo(){
        UI.getCurrent().getPage().executeJs("muteVideo()");
    }

    public void localHold(){
        UI.getCurrent().getPage().executeJs("localHold()");
    }

    public void callSendDTMF(String symbol) {
        UI.getCurrent().getPage().executeJs("callSendDTMF($0)", symbol);
    }

    public void makeCall(String dialNum) {
        UI.getCurrent().getPage().executeJs("makeCall($0)", dialNum);
    }

    @ClientCallable
    public void auEvent(EventType eventType,String event) {
        fireEvent(new ChangeEvent(this, false, eventType, event));
    }

    public static class ChangeEvent extends ComponentEvent<PhoneComponent> {
        private final String event;
        private final EventType eventType;
        public ChangeEvent(PhoneComponent source, boolean fromClient, EventType eventType, String event) {
            super(source, fromClient);
            this.event = event;
            this.eventType=eventType;
        }

        public String getEventValue() {
            return event;
        }
        public EventType getEventType() {
            return eventType;
        }
    }

    public void addChangeListener(ComponentEventListener<ChangeEvent> listener) {
        addListener(ChangeEvent.class, listener);
    }
}
