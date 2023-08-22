package ua.vg.vp.views.phone;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import ua.vg.vp.util.*;
import ua.vg.vp.views.MainLayout;

@PageTitle("Phone")
@Route(value = "Phone", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
@JsModule("./js/browserCall.js")
public class PhoneView extends Composite<VerticalLayout> {
    private EventType buttonCallMode;
    private final PhoneComponent phone = new PhoneComponent();

    private final H6 h6 = new H6();

    private final TextField dialTo = new TextField();

    private final Button buttonMuteVideo = new Button();

    private final Button buttonHold = new Button();

    private final Button buttonPutinPidoras = new Button();

    private final Button button1 = new Button();

    private final Button button2 = new Button();

    private final Button button3 = new Button();

    private final Button button4 = new Button();

    private final Button button5 = new Button();

    private final Button button6 = new Button();

    private final Button button7 = new Button();

    private final Button button8 = new Button();

    private final Button button9 = new Button();

    private final Button buttonStar = new Button();

    private final Button button0 = new Button();

    private final Button buttonSharp = new Button();

    private final Button buttonCall = new Button();

    private final Button buttonMuteAudio = new Button();

    private final Button buttonEndCall = new Button();


    public PhoneView() {

        getContent().setHeightFull();
        getContent().setWidthFull();
        HorizontalLayout layoutRow = new HorizontalLayout();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        VerticalLayout phoneVideoPadLayout = new VerticalLayout();
        layoutRow.setFlexGrow(1.0, phoneVideoPadLayout);
        phoneVideoPadLayout.setHeightFull();
        phoneVideoPadLayout.setWidth("85%");
        VideoComponent video = new VideoComponent();
        phoneVideoPadLayout.getStyle().set("border", "1px solid DarkOrange");
        phoneVideoPadLayout.setFlexGrow(1.0, video);
        phoneVideoPadLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, video);
        VerticalLayout layoutColumn3 = new VerticalLayout();
        layoutColumn3.addClassName(Gap.XSMALL);
        layoutColumn3.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth(null);
        h6.setText("STATUS");
        h6.setWidthFull();
        dialTo.setLabel("Dial to");
        dialTo.setRequiredIndicatorVisible(true);
        dialTo.setAllowedCharPattern("[0-9()+]");
        dialTo.setWidthFull();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        layoutRow2.addClassName(Gap.MEDIUM);
        buttonMuteVideo.setText("V");
        buttonMuteVideo.setEnabled(false);
        buttonHold.setText("H");
        buttonHold.setEnabled(false);
        buttonPutinPidoras.setText("P");
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        layoutRow3.addClassName(Gap.MEDIUM);
        button1.setText("1");
        button2.setText("2");
        button3.setText("3");
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        layoutRow4.addClassName(Gap.MEDIUM);
        button4.setText("4");
        button5.setText("5");
        button6.setText("6");
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        layoutRow5.addClassName(Gap.MEDIUM);
        button7.setText("7");
        button8.setText("8");
        button9.setText("9");
        HorizontalLayout layoutRow6 = new HorizontalLayout();
        layoutRow6.addClassName(Gap.MEDIUM);
        buttonStar.setText("*");
        button0.setText("0");
        buttonSharp.setText("#");
        HorizontalLayout layoutRow7 = new HorizontalLayout();
        layoutRow7.addClassName(Gap.MEDIUM);
        buttonCall.setText("D");
        buttonCall.setEnabled(false);
        buttonMuteAudio.setText("M");
        buttonMuteAudio.setEnabled(false);
        buttonEndCall.setText("E");
        buttonEndCall.setEnabled(false);
        getContent().add(layoutRow);
        layoutRow.add(phoneVideoPadLayout);
        phoneVideoPadLayout.add(video);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(h6);
        layoutColumn3.add(dialTo);
        layoutColumn3.add(layoutRow2);
        layoutRow2.add(buttonMuteVideo);
        layoutRow2.add(buttonHold);
        layoutRow2.add(buttonPutinPidoras);
        layoutColumn3.add(layoutRow3);
        layoutRow3.add(button1);
        layoutRow3.add(button2);
        layoutRow3.add(button3);
        layoutColumn3.add(layoutRow4);
        layoutRow4.add(button4);
        layoutRow4.add(button5);
        layoutRow4.add(button6);
        layoutColumn3.add(layoutRow5);
        layoutRow5.add(button7);
        layoutRow5.add(button8);
        layoutRow5.add(button9);
        layoutColumn3.add(layoutRow6);
        layoutRow6.add(buttonStar);
        layoutRow6.add(button0);
        layoutRow6.add(buttonSharp);
        layoutColumn3.add(layoutRow7);
        layoutRow7.add(buttonCall);
        layoutRow7.add(buttonMuteAudio);
        layoutRow7.add(buttonEndCall);
        initSoundComponents();
        getContent().add(phone);

        setButtonFunctions();
    }

    private void setButtonFunctions() {
        buttonPutinPidoras.addClickListener(buttonClickEvent -> dialTo.setValue("Путин хуйло!!! Кацапы пидорасы!!! Как вы заебали твари ёбаные"));

        buttonMuteVideo.addClickListener(buttonClickEvent -> {
            if (buttonCallMode == EventType.ANSWERED) {
                phone.muteVideo();
            }
        });

        buttonHold.addClickListener(buttonClickEvent -> {
            if (buttonCallMode == EventType.ANSWERED) {
                phone.localHold();
            }
        });

        buttonMuteAudio.addClickListener(buttonClickEvent -> {
            if (buttonCallMode == EventType.ANSWERED) {
                phone.muteAudio();
            }
        });

        button0.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "0");
            } else {
                phone.callSendDTMF("0");
            }
        });

        button1.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "1");
            } else {
                phone.callSendDTMF("1");
            }
        });

        button2.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "2");
            } else {
                phone.callSendDTMF("2");
            }
        });

        button3.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "3");
            } else {
                phone.callSendDTMF("3");
            }
        });

        button4.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "4");
            } else {
                phone.callSendDTMF("4");
            }
        });

        button5.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "5");
            } else {
                phone.callSendDTMF("5");
            }
        });

        button6.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "6");
            } else {
                phone.callSendDTMF("6");
            }
        });

        button7.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "7");
            } else {
                phone.callSendDTMF("7");
            }
        });

        button8.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "8");
            } else {
                phone.callSendDTMF("8");
            }
        });

        button9.addClickListener(buttonClickEvent -> {
            if (buttonCallMode != EventType.ANSWERED) {
                dialTo.setValue(dialTo.getValue() + "9");
            } else {
                phone.callSendDTMF("9");
            }
        });
        buttonStar.addClickListener(buttonClickEvent -> {
            if (buttonCallMode == EventType.ANSWERED) {
                phone.callSendDTMF("*");
            }
        });

        buttonSharp.addClickListener(buttonClickEvent -> {
            if (buttonCallMode == EventType.ANSWERED) {
                phone.callSendDTMF("#");
            }
        });

        buttonCall.addClickListener(buttonClickEvent -> {
            if (buttonCallMode == EventType.INCOMING_CALL) {
                phone.answer();
            } else {
                phone.makeCall(dialTo.getValue());
            }
        });
        buttonEndCall.addClickListener(buttonClickEvent -> {
            if (buttonCallMode == EventType.INCOMING_CALL || buttonCallMode == EventType.ANSWERED || buttonCallMode == EventType.CALLING) {
                phone.endCall();
            }
        });

        phone.addValueChangeListener(changeEvent -> {
            h6.setText(changeEvent.getEventValue());
            if (changeEvent.getEventType() == EventType.UA_STATE && changeEvent.getEventValue().equalsIgnoreCase("registered")) {
                buttonCall.setEnabled(true);
                buttonCall.setText("<MC>");
                buttonCall.setTooltipText("Make call");
            } else if (changeEvent.getEventType() == EventType.INCOMING_CALL) {
                UI.getCurrent().getPage().executeJs("window.isCallInProgress = true;");
                buttonCallMode = changeEvent.getEventType();
                buttonCall.setEnabled(true);
                buttonCall.setAutofocus(true);
                buttonCall.setText("<AC>");
                buttonCall.setTooltipText("Answer the call");
                buttonEndCall.setEnabled(true);
                buttonEndCall.setText("<EC>");
                buttonEndCall.setTooltipText("End the call");

            } else if (changeEvent.getEventType() == EventType.CALLING) {
                UI.getCurrent().getPage().executeJs("window.isCallInProgress = true;");
                buttonCallMode = changeEvent.getEventType();
                buttonCall.setEnabled(false);
                buttonCall.setText("<AC>");
                buttonCall.setTooltipText("Answer the call");
                buttonEndCall.setEnabled(true);
                buttonEndCall.setAutofocus(true);
                buttonEndCall.setText("<EC>");
                buttonEndCall.setTooltipText("End the call");
            } else if (changeEvent.getEventType() == EventType.ANSWERED) {
                UI.getCurrent().getPage().executeJs("window.isCallInProgress = true;");
                buttonCallMode = changeEvent.getEventType();

                buttonCall.setEnabled(false);
                buttonCall.setText("<MC>");
                buttonCall.setTooltipText("Make call");

                buttonHold.setEnabled(true);
                buttonHold.setText("<HC>");
                buttonHold.setTooltipText("Hold/Unhold call");

                buttonMuteAudio.setEnabled(true);
                buttonMuteAudio.setText("<MA>");
                buttonMuteAudio.setTooltipText("Mute/Unmute audio");

                buttonMuteVideo.setEnabled(true);
                buttonMuteVideo.setText("<MV>");
                buttonMuteVideo.setTooltipText("Mute/Unmute video");

            } else if (changeEvent.getEventType() == EventType.ENDED || changeEvent.getEventType() == EventType.FAILED) {
                UI.getCurrent().getPage().executeJs("window.isCallInProgress = false;");
                buttonCallMode = changeEvent.getEventType();
                dialTo.setValue("");
                buttonCall.setEnabled(true);
                buttonCall.setText("<MC>");
                buttonCall.setTooltipText("Make call");

                buttonHold.setEnabled(false);
                buttonHold.setText("<HC>");
                buttonHold.setTooltipText("Hold/Unhold local channel");

                buttonMuteAudio.setEnabled(false);
                buttonMuteAudio.setText("<MA>");
                buttonMuteAudio.setTooltipText("Mute/Unmute audio");

                buttonMuteVideo.setEnabled(false);
                buttonMuteVideo.setText("<MV>");
                buttonMuteVideo.setTooltipText("Mute/Unmute audio");

                buttonEndCall.setEnabled(false);
                buttonEndCall.setText("<EC>");
                buttonEndCall.setTooltipText("End the call");

            }
        });
    }
private void initSoundComponents(){
    RingToneComponent ringTone = new RingToneComponent();
    getContent().add(ringTone);
    RingBackToneComponent ringBackTone = new RingBackToneComponent();
    getContent().add(ringBackTone);
    DtmfToneComponent dtmfTone = new DtmfToneComponent();
    getContent().add(dtmfTone);
}


}
