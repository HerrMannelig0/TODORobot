package com.epam.file;

/**
 * Created by damian on 21.03.16.
 */
public class Link {
    private String linkAdress;
    private String typeForSearch;
    private String nameForSearch;
    private String fileName;

    public Link(String linkAdress, String typeForSearch, String nameForSearch) {
        this.linkAdress = linkAdress;
        this.typeForSearch = typeForSearch;
        this.nameForSearch = nameForSearch;
        this.fileName = createFileName();
    }

    public String getElementType() {
        return typeForSearch;
    }

    public void setTypeForSearch(String typeForSearch) {
        this.typeForSearch = typeForSearch;
    }

    public String getElementName() {
        return nameForSearch;
    }

    public void setNameForSearch(String nameForSearch) {
        this.nameForSearch = nameForSearch;
    }

    protected String createFileName() {
        return linkAdress.replaceAll("/", "");
    }

    public String getLinkAdress() {
        return linkAdress;
    }

    public String getFileName() {
        return fileName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if(linkAdress == link.linkAdress && typeForSearch == link.typeForSearch 
        		&& nameForSearch == link.nameForSearch) return true;
        if (!linkAdress.equals(link.linkAdress)) return false;
        if (!typeForSearch.equals(link.typeForSearch)) return false;
        return nameForSearch.equals(link.nameForSearch);

    }

    @Override
    public int hashCode() {
        int result = linkAdress.hashCode();
        result = 31 * result + typeForSearch.hashCode();
        result = 31 * result + nameForSearch.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return linkAdress + ' ' + typeForSearch + ' ' + nameForSearch;
    }

}
