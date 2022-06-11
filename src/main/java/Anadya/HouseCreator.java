package Anadya;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

public class HouseCreator
{
    private double x;
    private double y;
    private double measurement;

    public HouseCreator(double x, double y, double measurement)
    {
        this.x = x;
        this.y = y;
        this.measurement = measurement;
    }

    public void draw(Graphics2D g2)
    {
        //Roof points
        Point2D.Double p1 = new Point2D.Double(x, y - (measurement / 2));   //Top point roof
        Point2D.Double p2 = new Point2D.Double(x - (measurement / 2), y);   //Bottom left roof
        Point2D.Double p3 = new Point2D.Double(x + (measurement / 2), y);   //Bottom right roof

        //Roof lines
        Line2D.Double r1 = new Line2D.Double(p1, p2);   //Left side roof
        Line2D.Double r2 = new Line2D.Double(p1, p3);   //Right side roof

        //House starting coordinates and shape
        int xCoord = (int)p2.getX();
        int yCoord = (int)p2.getY();

        Rectangle house = new Rectangle(xCoord, yCoord, (int)measurement, (int)measurement);

        //Door starting coordinates and shape
        xCoord = (int)(p2.getX() + (measurement / 5));
        yCoord = (int)(p2.getY() + (measurement / 2));

        Rectangle door = new Rectangle(xCoord, yCoord,(int)(measurement / 4), (int)(measurement / 2)); //Door shape

        //Window starting coordinates and shape
        xCoord = xCoord + (int)(measurement / 2.5);
        yCoord = yCoord + (int)(measurement / 10);

        Rectangle window = new Rectangle(xCoord, yCoord, (int)(measurement / 4), (int)(measurement / 4)); //Windows shape

        g2.draw(r1);
        g2.draw(r2);
        g2.draw(house);
        g2.draw(door);
        g2.draw(window);

    }




}
