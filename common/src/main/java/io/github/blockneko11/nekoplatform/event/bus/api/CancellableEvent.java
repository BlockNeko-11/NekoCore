package io.github.blockneko11.nekoplatform.event.bus.api;

import lombok.Getter;

@Getter
public abstract class CancellableEvent extends Event {
    private boolean cancelled;

    public void cancel() {
        this.cancelled = true;
    }
}
