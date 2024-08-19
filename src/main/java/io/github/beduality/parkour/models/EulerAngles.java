package io.github.beduality.parkour.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EulerAngles {
    private float pitch;
    private float yaw;

    public EulerAngles(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }
}
