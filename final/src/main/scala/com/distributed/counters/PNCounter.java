package com.distributed.counters;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PNCounter {
    private Map<String, Long> nodeVals = new HashMap<String, Long>();

    public GCounter incCounter;
    public GCounter decCounter;

    public PNCounter(Set<String> nodes, GCounter incCounter, GCounter decCounter) {
        for (String k : nodes) { nodeVals.put( k, new Long(0) ); }
        this.incCounter = incCounter;
        this.decCounter = decCounter;
    }

    public void inc(String nodeName, long i) {
        incCounter.inc(nodeName, i);
    }

    public void dec(String nodeName, long i) {
        decCounter.inc(nodeName, i);
    }

    public long value() {
        return incCounter.value() - decCounter.value();
    }

    public long getNodeValue(String nodeName) {
        return nodeVals.get(nodeName);
    }

    public PNCounter merge(PNCounter that) {
        GCounter incMerge = incCounter.merge(that.incCounter);
        GCounter decMerge = decCounter.merge(that.decCounter);
        return new PNCounter(nodeVals.keySet(), incMerge, decMerge);
    }
}
