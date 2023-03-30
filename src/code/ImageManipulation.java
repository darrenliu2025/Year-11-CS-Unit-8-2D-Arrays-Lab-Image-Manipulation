package code;

import image.APImage;
import image.Pixel;

import java.util.ArrayList;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage image = new APImage("cyberpunk2077.jpg");
        image.draw();
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int k = 0; k < image.getHeight(); k++) {
                Pixel temp = image.getPixel(i, k);
                int average = getAverageColour(temp);
                temp.setBlue(average);
                temp.setRed(average);
                temp.setGreen(average);
                image.setPixel(i, k, temp);
            }
        }
        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        int red = pixel.getRed();
        int blue = pixel.getBlue();
        int green = pixel.getGreen();
        return (red + blue + green) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int k = 0; k < image.getHeight(); k++) {
                Pixel temp = image.getPixel(i, k);
                int average = getAverageColour(temp);
                if (average < 128) {
                    temp.setGreen(0);
                    temp.setBlue(0);
                    temp.setRed(0);
                }
                else {
                    temp.setGreen(255);
                    temp.setBlue(255);
                    temp.setRed(255);
                }
                image.setPixel(i, k, temp);
            }
        }
        image.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = width-1; i > 0; i--){
            for (int k = height -1; k > 0; k--){

                int red = image.getPixel(i, k).getRed();
                int blue = image.getPixel(i, k).getBlue();
                int green = image.getPixel(i, k).getGreen();
                int avg = (red + blue + green) / 3;

                int redLeft = image.getPixel(i-1, k).getRed();
                int blueLeft = image.getPixel(i-1, k).getBlue();
                int greenLeft = image.getPixel(i-1, k).getGreen();
                int avgLeft = (redLeft + blueLeft + greenLeft) / 3;

                int redBelow = image.getPixel(i, k-1).getRed();
                int blueBelow = image.getPixel(i, k-1).getBlue();
                int greenBelow = image.getPixel(i, k-1).getGreen();
                int avgBelow = (redBelow + blueBelow + greenBelow) / 3;

                int leftDifference = (avg-avgLeft);
                int belowDifference = (avg-avgBelow);

                green = red = blue = 255;
                if(leftDifference+1 > threshold || belowDifference+1 > threshold) {
                    red = blue = green = 0;
                }

                Pixel pixel= new Pixel(red, blue, green);
                image.setPixel(i, k, pixel);
            }
        }
        image.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        ArrayList<Pixel> array = new ArrayList<>();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int k = 0; k < image.getHeight(); k++)
                array.add(image.getPixel(i, k));
        }

        int size = 0;
        for (int i = image.getWidth()-1; i + 1 > 0; i--) {
            for (int k = 0; k < image.getHeight(); k++) {
                image.setPixel(i, k, array.get(size));
                size++;
            }
        }
        image.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage imageNew = new APImage(image.getHeight(), image.getWidth());

        for (int i = 0; i < image.getWidth(); ++i) {
            for (int k = 0; k < image.getHeight(); k++) {
                Pixel pixel = image.getPixel(i, k);
                imageNew.setPixel(image.getHeight()-k-1, i, pixel);
            }
        }
        imageNew.draw();
    }

}