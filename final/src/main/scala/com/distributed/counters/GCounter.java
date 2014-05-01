package com.distributed.counters;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GCounter {

    private Map<String, Long> nodeVals = new HashMap<String, Long>();

    public GCounter(Set<String> nodes) {
        for (String k : nodes) { nodeVals.put( k, new Long(0) ); }
    }

    public void inc(String nodeName) { inc(nodeName, 1); }

    public void inc(String nodeName, long i) {
        Long current = nodeVals.get(nodeName);
        Long newValue = current + i;
        nodeVals.put(nodeName, newValue);
    }

    public long value() {
        Long sum = new Long(0);
        for (String k : nodeVals.keySet()) { sum += nodeVals.get(k); }
        return sum;
    }

    public long getNodeValue(String nodeName) {
        return nodeVals.get(nodeName);
    }

    public GCounter merge(GCounter that) {
        Map<String, Long> mergedVals = new HashMap<String, Long>();

        GCounter mergedCounter = new GCounter(nodeVals.keySet());

        for (String k : nodeVals.keySet()) {
            Long thatValue = that.getNodeValue(k);
            Long thisValue = nodeVals.get(k);

            if (thatValue > thisValue) {
                mergedCounter.inc(k, thatValue);
            }
            else {
                mergedCounter.inc(k, thisValue);
            }
        }

        return mergedCounter;
    }


    private long valOrZero(Long l) {
        if (l != null ) return l.longValue();
        return 0;
    }
}