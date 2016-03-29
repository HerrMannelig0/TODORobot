package com.epam.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by damian on 21.03.16.
 */
public class FileLinkHandler {
    private final String fileName = "src/main/resources/FreeBooksAdressSite.txt";
    private List<Link> linkList = new ArrayList<Link>();
    private List<String> urlList = new ArrayList<String>();
    private final URL freeBooksAdressSiteUrl = createUrlToFile();

    public FileLinkHandler() {
        createUrlToFile();
       
        try {
            updateLinkList();
            updateURLList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateURLList() {
        for(Link link : linkList){
            urlList.add(link.getLinkAdress());
        }
    }

    private void updateLinkList() throws FileNotFoundException {
        linkList.addAll(readLinksFromFile(new File(fileName)));
        
    }

    private URL createUrlToFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL freeBooksAdressSiteUrl = classLoader.getResource(fileName);
        return freeBooksAdressSiteUrl;
    }
    
    public List<Link> readLinksFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        List<Link> links = new ArrayList<>();
        
        while(scanner.hasNextLine()){
            String[] splittedLine = scanner.nextLine().split(" ");
            if (doesLinkContainThreeParts(splittedLine)) {
                Link link = new Link(splittedLine[0], splittedLine[1], splittedLine[2]);
                links.add(link);
            }
        }
        scanner.close();
        return links;
    }
    
    

    private boolean doesLinkContainThreeParts(String[] splittedLine) {
        return splittedLine.length == 3;
}

    public void writeLinksToFile(List<Link> linkList) {
        try (PrintWriter printWriter = new PrintWriter("src/main/resources/" + fileName)) {
            for (int i = 0; i < linkList.size(); i++) {
                printWriter.println(linkList.get(i).toString());
            }
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public List<Link> getLinksList() {
        return linkList;
    }

    public List<String> getUrlList() {
        return urlList;
    }
}