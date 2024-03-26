package esper.api4eventprocessing.listeners;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

public class EventListener implements UpdateListener {

    @Override
    public void update(EventBean[] oldComplexEvents, EventBean[] newComplexEvents, EPStatement epStatement, EPRuntime epRuntime) {
        if (newComplexEvents != null){
            System.out.printf("Chivato del modulo hay un evento complejo");
        }
    }
}
