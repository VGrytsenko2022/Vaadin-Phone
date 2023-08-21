package ua.vg.vp.util;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

@Tag("audio")
public class DtmfToneComponent extends com.vaadin.flow.component.Component {
    public DtmfToneComponent() {
        setId("dtmfTone");
        setSrc("sounds/dtmf.mp3");
    }

    public void setSrc(StreamResource resource) {
        getElement().setAttribute("src", resource);
    }

    public void setSrc(String resource) {
        getElement().setAttribute("src", resource);
    }
}
