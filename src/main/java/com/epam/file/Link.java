package com.epam.file;

/**
 * Class {@code Link} defines elements of expanded bookstore's link, 
 * and contains link address and tags, that are used to find a book.
 */
public class Link {
    private String linkAdress;
    private String typeForSearch;
    private String titleTag;
    private String authorTag;
    private String priceTag;
    private String keywordsTag;

    /**
     * Link constructor.
     * @param bookstoreAddress
     * @param typeForSearch
     * @param titleTag
     * @param authorTag
     * @param priceTag
     * @param keywordsTag
     */
    public Link(String bookstoreAddress, String typeForSearch, String titleTag, String authorTag, String priceTag, String keywordsTag) {
        this.linkAdress = bookstoreAddress;
        this.typeForSearch = typeForSearch;
        this.titleTag = titleTag;
        this.authorTag = authorTag;
        this.priceTag = priceTag;
        this.keywordsTag = keywordsTag;
    }

    public String getElementType() {
        return typeForSearch;
    }

    public String getLinkAdress() {
        return linkAdress;
    }

    public String getTitleTag() {
        return titleTag;
    }

    protected String createFileName() {
        return linkAdress.replaceAll("/", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if(linkAdress == link.linkAdress && typeForSearch == link.typeForSearch 
        		&& titleTag == link.titleTag) return true;
        if (!linkAdress.equals(link.linkAdress)) return false;
        if (!typeForSearch.equals(link.typeForSearch)) return false;
        return titleTag.equals(link.titleTag);

    }

    @Override
    public int hashCode() {
        int result = linkAdress.hashCode();
        result = 31 * result + typeForSearch.hashCode();
        result = 31 * result + titleTag.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return linkAdress + ' ' + typeForSearch + ' ' + titleTag + ' ' + authorTag + ' ' + priceTag + ' '
        		+ keywordsTag;
    }

	public String getAuthorTag() {
		return authorTag;
	}

	public String getPriceTag() {
		return priceTag;
	}

	public String getKeywordsTag() {
		return keywordsTag;
	}

}
