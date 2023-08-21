package ua.vg.vp.util;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

@Tag("audio")
public class RingBackToneComponent extends com.vaadin.flow.component.Component {
    public RingBackToneComponent() {
        setId("ringBackTone");
        setSrc("sounds/outgoing.mp3");
        getElement().setAttribute("loop", "true");
    }

    public void setSrc(StreamResource resource) {
        getElement().setAttribute("src", resource);
    }

    public void setSrc(String resource) {
        getElement().setAttribute("src", resource);
    }
}
