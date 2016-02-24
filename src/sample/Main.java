package sample;

import java.io.IOException;

public class Main  {

    public static void main(String[] args) throws IOException {

        String url = "ru.transcend-info.com/product/memorycards";
        String path = "D:\\sd";
        GetHtmlPages d = new GetHtmlPages(url, path);
        d.createDir();
        d.check();

    }
}
