package app.entities;

public class Mount {
    private int mountId;
    private float mountPrice;
    private String mountType;

    public Mount(int mountId, float mountPrice, String mountType) {
        this.mountId = mountId;
        this.mountPrice = mountPrice;
        this.mountType = mountType;
    }

    public int getMountId() {
        return mountId;
    }

    public void setMountId(int mountId) {
        this.mountId = mountId;
    }

    public float getMountPrice() {
        return mountPrice;
    }

    public void setMountPrice(float mountPrice) {
        this.mountPrice = mountPrice;
    }

    public String getMountType() {
        return mountType;
    }

    public void setMountType(String mountType) {
        this.mountType = mountType;
    }

    @Override
    public String toString() {
        return "Mount{" +
                "mountId=" + mountId +
                ", mountPrice=" + mountPrice +
                ", mountType='" + mountType + '\'' +
                '}';
    }
}
