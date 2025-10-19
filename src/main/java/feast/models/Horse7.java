package feast.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Horse7 {

    @Autowired
    private Camel8 camel8;

    @Override
    public String toString() {
        return "which is stuffed inside a camel, " + camel8.toString();
    }
}
