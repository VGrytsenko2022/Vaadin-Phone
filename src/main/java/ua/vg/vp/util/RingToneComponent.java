package ua.vg.vp.util;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

@Tag("audio")
public class RingToneComponent extends com.vaadin.flow.component.Component {
    public RingToneComponent() {
        setId("ringTone");
        setSrc("sounds/incoming.mp3");
        getElement().setAttribute("loop", "true");
    }

    public void setSrc(StreamResource resource) {
        getElement().setAttribute("src", resource);
    }

    public void setSrc(String resource) {
        getElement().setAttribute("src", resource);
    }
}
