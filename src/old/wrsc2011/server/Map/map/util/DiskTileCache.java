/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map.util;

import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.image.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;
import org.jdesktop.swingx.mapviewer.wms.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Alexander
 */
public class DiskTileCache extends TileCache {

    protected final File tileCacheDir = new File(System.getProperty("user.home") + File.separator + "OSMTiles");
    protected Map<URI, SBufferedImage> imgmap = new HashMap<URI, SBufferedImage>();
    protected LinkedList<URI> imgmapAccessQueue = new LinkedList<URI>();
    protected int imagesize = 0;
    protected Map<URI, byte[]> bytemap = new HashMap<URI, byte[]>();
    protected LinkedList<URI> bytemapAccessQueue = new LinkedList<URI>();
    protected int bytesize = 0;

    public DiskTileCache() {
        super();
        try {
            if (!this.tileCacheDir.exists()) this.tileCacheDir.mkdir();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(URI uri, byte[] bimg, BufferedImage img) {
        synchronized (bytemap) {
            while (bytesize > 1000 * 1000 * 50) {
                URI olduri = bytemapAccessQueue.removeFirst();
                byte[] oldbimg = bytemap.remove(olduri);
                bytesize -= oldbimg.length;
                p("removed 1 img from byte cache");
            }

            bytemap.put(uri, bimg);
            bytesize += bimg.length;
            bytemapAccessQueue.addLast(uri);
        }
        addToImageCache(uri, img);
    }

    public BufferedImage get(URI uri) throws IOException {
        synchronized (imgmap) {
            if (imgmap.containsKey(uri)) {
                imgmapAccessQueue.remove(uri);
                imgmapAccessQueue.addLast(uri);
                return imgmap.get(uri).bi;
            }
        }
        synchronized (bytemap) {
            if (bytemap.containsKey(uri)) {
                p("retrieving from bytes");
                bytemapAccessQueue.remove(uri);
                bytemapAccessQueue.addLast(uri);
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytemap.get(uri)));
                addToImageCache(uri, img);
                return img;
            }
        }
        synchronized (imgmap) {
            SBufferedImage simg = readSBufferedImage(createFileName(uri));
            if (simg != null) return simg.bi;
        }
        return null;
    }

    public void needMoreMemory() {
        imgmap.clear();
        p("HACK! need more memory: freeing up memory");
    }

    protected void addToImageCache(final URI uri, final BufferedImage img) {
        synchronized (imgmap) {
            while (imagesize > 1000 * 1000 * 50) {
                URI olduri = imgmapAccessQueue.removeFirst();
                SBufferedImage soldimg = imgmap.remove(olduri);
                BufferedImage oldimg = soldimg.bi;
                imagesize -= oldimg.getWidth() * oldimg.getHeight() * 4;
                p("removed 1 img from image cache");
            }

            imgmap.put(uri, new SBufferedImage(img));
            imagesize += img.getWidth() * img.getHeight() * 4;
            imgmapAccessQueue.addLast(uri);
            writeSBufferedImage(createFileName(uri), new SBufferedImage(img));

        }
        p("added to cache: " + " uncompressed = " + imgmap.keySet().size() + " / " + imagesize / 1000 + "k" + " compressed = " + bytemap.keySet().size() + " / " + bytesize / 1000 + "k");
    }

    private SBufferedImage readSBufferedImage(File f) {
        SBufferedImage result = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            result = (SBufferedImage)ois.readObject();
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

    private void writeSBufferedImage(File f, SBufferedImage img) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(img);
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private File createFileName(URI uri) {
        File result = new File(this.tileCacheDir.toString() + File.separator + createString(uri));
        return result;
    }

    private String createString(URI uri) {
        String result = uri.toString();
        result = result.replace(":", "_");
        result = result.replace("/", "_");
        //System.out.println(result);
        return result;
    }

    private void p(String string) {
        //System.out.println(string);
    }

    public class SBufferedImage implements Serializable {

        public BufferedImage bi = null;

        public SBufferedImage(BufferedImage bi) {
            this.bi = bi;
        }

        private void writeObject(java.io.ObjectOutputStream out) throws IOException {
            ImageIO.write(bi, "PNG", out);
        }

        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
            bi = ImageIO.read(in);
        }
    }
}
