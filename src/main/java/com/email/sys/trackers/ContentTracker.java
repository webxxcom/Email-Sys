package com.email.sys.trackers;

import com.email.sys.Contents;
import javafx.scene.Node;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContentTracker extends Tracker<Map.Entry<Contents, Node>> {

}
