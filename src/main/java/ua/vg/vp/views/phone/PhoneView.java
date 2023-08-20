package ua.vg.vp.views.phone;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import ua.vg.vp.views.MainLayout;

@PageTitle("Phone")
@Route(value = "Phone", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class PhoneView extends Composite<VerticalLayout> {

    private HorizontalLayout layoutRow = new HorizontalLayout();

    private VerticalLayout layoutColumn2 = new VerticalLayout();

    private MessageList messageList = new MessageList();

    private VerticalLayout layoutColumn3 = new VerticalLayout();

    private H6 h6 = new H6();

    private TextField textField = new TextField();

    private HorizontalLayout layoutRow2 = new HorizontalLayout();

    private Button buttonSecondary = new Button();

    private Button buttonSecondary2 = new Button();

    private Button buttonSecondary3 = new Button();

    private HorizontalLayout layoutRow3 = new HorizontalLayout();

    private Button buttonSecondary4 = new Button();

    private Button buttonSecondary5 = new Button();

    private Button buttonSecondary6 = new Button();

    private HorizontalLayout layoutRow4 = new HorizontalLayout();

    private Button buttonSecondary7 = new Button();

    private Button buttonSecondary8 = new Button();

    private Button buttonSecondary9 = new Button();

    private HorizontalLayout layoutRow5 = new HorizontalLayout();

    private Button buttonSecondary10 = new Button();

    private Button buttonSecondary11 = new Button();

    private Button buttonSecondary12 = new Button();

    private HorizontalLayout layoutRow6 = new HorizontalLayout();

    private Button buttonSecondary13 = new Button();

    private Button buttonSecondary14 = new Button();

    private Button buttonSecondary15 = new Button();

    private HorizontalLayout layoutRow7 = new HorizontalLayout();

    private Button buttonSecondary16 = new Button();

    private Button buttonSecondary17 = new Button();

    private Button buttonSecondary18 = new Button();

    public PhoneView() {
        getContent().setHeightFull();
        getContent().setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setHeightFull();
        layoutColumn2.setWidth(null);
        layoutColumn2.setFlexGrow(1.0, messageList);
        messageList.setWidthFull();
        setMessageListSampleData(messageList);
        layoutColumn3.addClassName(Gap.XSMALL);
        layoutColumn3.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth(null);
        h6.setText("STATUS");
        h6.setWidthFull();
        textField.setLabel("Dial to");
        textField.setWidthFull();
        layoutRow2.addClassName(Gap.MEDIUM);
        buttonSecondary.setText("V");
        buttonSecondary2.setText("H");
        buttonSecondary3.setText("P");
        layoutRow3.addClassName(Gap.MEDIUM);
        buttonSecondary4.setText("1");
        buttonSecondary5.setText("2");
        buttonSecondary6.setText("3");
        layoutRow4.addClassName(Gap.MEDIUM);
        buttonSecondary7.setText("4");
        buttonSecondary8.setText("5");
        buttonSecondary9.setText("6");
        layoutRow5.addClassName(Gap.MEDIUM);
        buttonSecondary10.setText("7");
        buttonSecondary11.setText("8");
        buttonSecondary12.setText("0");
        layoutRow6.addClassName(Gap.MEDIUM);
        buttonSecondary13.setText("*");
        buttonSecondary14.setText("0");
        buttonSecondary15.setText("#");
        layoutRow7.addClassName(Gap.MEDIUM);
        buttonSecondary16.setText("C");
        buttonSecondary17.setText("M");
        buttonSecondary18.setText("E");
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(messageList);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(h6);
        layoutColumn3.add(textField);
        layoutColumn3.add(layoutRow2);
        layoutRow2.add(buttonSecondary);
        layoutRow2.add(buttonSecondary2);
        layoutRow2.add(buttonSecondary3);
        layoutColumn3.add(layoutRow3);
        layoutRow3.add(buttonSecondary4);
        layoutRow3.add(buttonSecondary5);
        layoutRow3.add(buttonSecondary6);
        layoutColumn3.add(layoutRow4);
        layoutRow4.add(buttonSecondary7);
        layoutRow4.add(buttonSecondary8);
        layoutRow4.add(buttonSecondary9);
        layoutColumn3.add(layoutRow5);
        layoutRow5.add(buttonSecondary10);
        layoutRow5.add(buttonSecondary11);
        layoutRow5.add(buttonSecondary12);
        layoutColumn3.add(layoutRow6);
        layoutRow6.add(buttonSecondary13);
        layoutRow6.add(buttonSecondary14);
        layoutRow6.add(buttonSecondary15);
        layoutColumn3.add(layoutRow7);
        layoutRow7.add(buttonSecondary16);
        layoutRow7.add(buttonSecondary17);
        layoutRow7.add(buttonSecondary18);
    }

    private void setMessageListSampleData(MessageList messageList) {
        MessageListItem message1 = new MessageListItem("Nature does not hurry, yet everything gets accomplished.",
                LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC), "Matt Mambo");
        message1.setUserColorIndex(1);
        MessageListItem message2 = new MessageListItem(
                "Using your talent, hobby or profession in a way that makes you contribute with something good to this world is truly the way to go.",
                LocalDateTime.now().minusMinutes(55).toInstant(ZoneOffset.UTC), "Linsey Listy");
        message2.setUserColorIndex(2);
        messageList.setItems(message1, message2);
    }
}
