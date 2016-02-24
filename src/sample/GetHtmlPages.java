package sample;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Сергей on 22.02.2016.
 */
public class GetHtmlPages {
    String filepath;
    List<String> links;
    String baseurl;
    String url;
    String path;

    GetHtmlPages(String surl, String path){
        this.url = this.baseurl = surl;
        this.path = path;
    }

    public void createDir(){
        InputStream in = null;
        OutputStream out = null;
        File f = new File(path + "\\" + baseurl);
        f.mkdirs();
        try {
            URL url = new URL("http://" + this.baseurl);
            URLConnection conn = url.openConnection();
            conn.connect();
            in = conn.getInputStream();
            String filepath = this.path + "\\" + this.baseurl + "\\index.html";
            this.filepath = filepath;
            System.out.println(filepath);
            out = new FileOutputStream(filepath);
            byte buffer[] = new byte[1024];
            int c = 0;
            while ((c = in.read(buffer)) > 0) {
                out.write(buffer, 0, c);
            }
            out.flush();
        } catch (IOException e) {
            System.out.println("File " + baseurl + " not found at server");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }

    }


    public void check(){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(this.filepath));
            String s;
            while ((s = bf.readLine()) != null){
                sb.append(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String aTag = "(?i)<a([^>]+)>(.+?)</a>";
        String hTag = "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

        Pattern paTag = Pattern.compile(aTag);
        Pattern phTag = Pattern.compile(hTag);

        links = new ArrayList<String>();

        Matcher maTag = paTag.matcher(sb);
        while(maTag.find()){
            String href = maTag.group(1);
            Matcher mhTag = phTag.matcher(href);
            while (mhTag.find()){
                String link = mhTag.group(1);
                link = link.replaceAll("\"|\'|http://","");
                links.add(link);
                System.out.println(link);
            }
            for (String s : links){
                        this.baseurl = this.url + s;
                        createDir();

            }
        }

    }

}
