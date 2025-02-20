package com.email.sys.trackers;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class Tracker<T> {
    protected Deque<T> back = new ArrayDeque<>();
    protected Deque<T> front = new ArrayDeque<>();

    public T back(){
        if(back.size() > 1) {
            front.push(back.pop());
            return back.peek();
        }
        return null;
    }

    public T forth(){
        if(!front.isEmpty()) {
            back.push(front.pop());
            return front.peek();
        }
        return null;
    }

    public void remember(T what){
        back.push(what);
        front.clear();
    }

    public void forgetAll() {
        back.clear();
        front.clear();
    }
}
