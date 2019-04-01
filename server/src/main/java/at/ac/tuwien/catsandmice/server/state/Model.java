package at.ac.tuwien.catsandmice.server.state;

import com.google.gson.annotations.Expose;

import java.util.Objects;
import java.util.UUID;

public class Model {

    @Expose
    private String uuid;

    public Model() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(uuid, model.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Model{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
