package CrossPlatformerV2.game.world.creation;

import CrossPlatformerV2.game.world.Tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ChunkCreator {


    public static CreatedChunkData createChunkData(int w, int h){
        float[][] noise = GeneratePerlinNoise(generateWithNoise(w,h,System.currentTimeMillis()),7,0.3f);
        Tile[][] tiles = new Tile[w][h];
        for(int x=0;x<noise.length;x++) {
            for (int y = 0; y < noise[0].length; y++) {
                float n = 1f-noise[x][y];
                if(n <0.3f) tiles[x][y] = Tile.getFromId(1);
                else if(n >0.3f && n <0.4f) tiles[x][y] = Tile.getFromId(2);
                else if(n >0.4f && n <0.75f) tiles[x][y] = Tile.getFromId(3);
                else if(n >0.75f && n <=1f) tiles[x][y] = Tile.getFromId(4);
                else tiles[x][y] = Tile.getFromId(0);
            }
        }


        return new CreatedChunkData(w,h,tiles);
    }

    private static void writeImage(float[][] noise){
        BufferedImage bufferedImage = new BufferedImage(noise.length,noise[0].length,BufferedImage.TYPE_INT_RGB);
        var g = bufferedImage.createGraphics();
        for(int x=0;x<noise.length;x++) {
            for (int y = 0; y < noise[0].length; y++) {
                float n = 1f-noise[x][y];
                if(n <0.3f) g.setColor(new Color(0,0,255));
                else if(n >0.3f && n <0.4f) g.setColor(new Color(251,255,0));
                else if(n >0.4f && n <0.75f) g.setColor(new Color(7,201,0));
                else if(n >0.75f && n <=1f) g.setColor(new Color(100,100,100));
                g.drawRect(x,y,1,1);
            }
        }
        g.dispose();
        File file = new File("test.png");
        try {
            if(!file.exists()) file.createNewFile();
            ImageIO.write(bufferedImage,"PNG",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static float[][] generateWithNoise(int w,int h,long seed){
        Random random = new Random(seed);
        float[][] noise = new float[w][h];
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                noise[x][y]= random.nextFloat() % 1;
            }
        }
        return noise;
    }

    private static float[][] GenerateSmoothNoise(float[][] baseNoise,int octave){
        int w = baseNoise.length;
        int h = baseNoise[0].length;

        float[][] smoothNoise = new float[w][h];
        int samplePeriod = 1 << octave;
        float sampleFrequency = 1f / samplePeriod;

        for(int x=0;x<w;x++) {
            int sample_i0 = (x / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % w; //wrap around
            float horizontal_blend = (x - sample_i0) * sampleFrequency;
            for (int y = 0; y < h; y++) {
                //calculate the vertical sampling indices
                int sample_j0 = (y / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % h; //wrap around
                float vertical_blend = (y - sample_j0) * sampleFrequency;

                float top = Interpolate(baseNoise[sample_i0][sample_j0],
                        baseNoise[sample_i1][sample_j0], horizontal_blend);

                float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
                        baseNoise[sample_i1][sample_j1], horizontal_blend);

                smoothNoise[x][y] = Interpolate(top, bottom, vertical_blend);
            }
        }
        return smoothNoise;
    }

    private static float Interpolate(float x0, float x1, float alpha)
    {
        return x0 * (1 - alpha) + alpha * x1;
    }

    private static float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount,float persistance)
    {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing


        //generate smooth noise
        for (int i = 0; i< octaveCount; i++)
        {
            smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
        }

        float[][] perlinNoise = new float[width][height];
        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        //blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--)
        {
            amplitude *= persistance;
            totalAmplitude += amplitude;

            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        //normalisation
        for (int i = 0; i <width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }
}
