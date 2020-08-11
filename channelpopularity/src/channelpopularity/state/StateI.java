package channelpopularity.state;

public interface StateI {

    public void addVideo(String VideoName);
    public void removeVideo(String VideoName);
    public void metrics(String VideoName,String viewsIn,String likesIn,String dislikesIn);
    public void adRequest(String VideoName, String length);


}
