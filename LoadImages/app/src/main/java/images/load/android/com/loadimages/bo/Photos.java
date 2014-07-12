package images.load.android.com.loadimages.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photos {

    @JsonProperty("photos")
    private PhotoInfo mPhotoInfo;

    public PhotoInfo getPhotoInfo() {
        return mPhotoInfo;
    }

    public void setPhotoInfo(PhotoInfo photoInfo) {
        mPhotoInfo = photoInfo;
    }

    public static class PhotoInfo {
        @JsonProperty("page")
        private int mPage;

        @JsonProperty("pages")
        private int mPages;

        @JsonProperty("perpage")
        private int mPerPage;

        @JsonProperty("total")
        private int mTotal;

        @JsonProperty("photo")
        private List<PhotoObject> mPhotoList;

        public int getPage() {
            return mPage;
        }

        public void setPage(int page) {
            mPage = page;
        }

        public int getPages() {
            return mPages;
        }

        public void setPages(int pages) {
            mPages = pages;
        }

        public int getPerPage() {
            return mPerPage;
        }

        public void setPerPage(int perPage) {
            mPerPage = perPage;
        }

        public int getTotal() {
            return mTotal;
        }

        public void setTotal(int total) {
            mTotal = total;
        }

        public List<PhotoObject> getPhotoList() {
            return mPhotoList;
        }

        public void setPhotoList(List<PhotoObject> photoList) {
            mPhotoList = photoList;
        }
    }
}
