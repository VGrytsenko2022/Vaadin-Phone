package ua.vg.vp.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

@Tag("video")
public class VideoComponent extends Component {
    
    public VideoComponent() {
        setId("localVideo");
        getElement().setAttribute("autoplay", "true");
        getElement().setAttribute("loop", "true");

    }
    public void setSrc(StreamResource resource) {
        getElement().setAttribute("src", resource);
    }

    public void setSrc(String resource) {
        getElement().setAttribute("src", resource);
    }
    
}
