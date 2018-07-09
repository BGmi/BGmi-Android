package bgmi.app.bgmi_android.models;

import java.util.Map;

public class Bangumi {
    private int id;
    private int status;
    private int updated_time;
    private int episode;

    private String update_time;
    private String name;
    private String bangumi_name;
    private String cover;

    private Map<String, Map<String, String>> player;

    public String getUpdate_time() {
        return update_time;
    }

    public String getBangumi_name() {
        return bangumi_name;
    }

    public String getCover() {
        return cover;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getEpisode() {
        return episode;
    }

    public int getUpdated_time() {
        return updated_time;
    }

    public int getStatus() {
        return status;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setBangumi_name(String bangumi_name) {
        this.bangumi_name = bangumi_name;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setUpdated_time(int updated_time) {
        this.updated_time = updated_time;
    }

    public Map<String, Map<String, String>> getPlayer() {
        return player;
    }

    public void setPlayer(Map<String, Map<String, String>> player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "<Bangumi: " + this.bangumi_name + ">";
    }
}

