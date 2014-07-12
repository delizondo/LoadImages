package images.load.android.com.loadimages.bo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoObject {

    @JsonProperty("id")
    private long mId;

    @JsonProperty("owner")
    private String mOwner;

    @JsonProperty("secret")
    private String mSecret;

    @JsonProperty("server")
    private String mServer;

    @JsonProperty("farm")
    private int mFarm;

    @JsonProperty("title")
    private String mTitle;

    @JsonProperty("ispublic")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private boolean mIsPublic;

    @JsonProperty("isfriend")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private boolean mIsFriend;

    @JsonProperty("isfamily")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private boolean mIsFamily;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getSecret() {
        return mSecret;
    }

    public void setSecret(String secret) {
        mSecret = secret;
    }

    public String getServer() {
        return mServer;
    }

    public void setServer(String server) {
        mServer = server;
    }

    public int getFarm() {
        return mFarm;
    }

    public void setFarm(int farm) {
        mFarm = farm;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isPublic() {
        return mIsPublic;
    }

    public void setPublic(boolean isPublic) {
        mIsPublic = isPublic;
    }

    public boolean isFriend() {
        return mIsFriend;
    }

    public void setFriend(boolean isFriend) {
        mIsFriend = isFriend;
    }

    public boolean isFamily() {
        return mIsFamily;
    }

    public void setFamily(boolean isFamily) {
        mIsFamily = isFamily;
    }
}
