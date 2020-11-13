package com.thoughtworks.argocd.autosync.domain.branch;

public class Branch {

    private String ref;

    private String url;

    private Object object;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }



    @Override
    public String toString() {
        return "Branch{" +
                "ref='" + ref + '\'' +
                ", url='" + url + '\'' +
                ", object=" + object +
                '}';
    }
}
