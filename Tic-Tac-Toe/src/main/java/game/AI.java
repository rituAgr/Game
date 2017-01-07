package game;

import java.io.*;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Created by Ritu on 12/27/16.
 */
public class AI
{
    ArrayList<position> corners;
    ArrayList<position> middles;
    AI()
    {
        corners=new ArrayList<>();
        corners.add(new position(1,1));
        corners.add(new position(1,3));
        corners.add(new position(3,3));
        corners.add(new position(3,1));

        middles=new ArrayList<>();
        middles.add(new position(1,2));
        middles.add(new position(2,1));
        middles.add(new position(2,3));
        middles.add(new position(3,2));

    }

  String getPosition_onBoard(position p)
  {
      if(p==null)
          return null;
      int r=p.row;
      int c=p.col;
      if(r==2&&c==2)
          return "Center";
      if(r==c||Math.abs(r-c)==2)
        return "Corner";
      return "Middle";
  }
  //This function with help of other helper func relative position of given position
    position NextRelative_position(position p)
    {
        String place=getPosition_onBoard(p);
        if(place.equals("Center"))
            return p;
        if(place.equals("Corner"))
            return get_ClockwiseCorner(p);
        return get_ClockwiseMiddle(p);
    }
    //helper func
    boolean checkEqual(position p1,position p2)
    {
        if((p1.row==p2.row)&&(p1.col==p2.col))
            return true;
        return false;
    }
    //helper func
    position get_ClockwiseCorner(position p)
    {
        if(checkEqual(corners.get(3),p))
            return corners.get(0);
        for(int i=0;i<3;i++)
            if(checkEqual(corners.get(i),p))
                return corners.get(i+1);
        return null;
    }
    //helper func
    position get_ClockwiseMiddle(position p)
    {
        if(checkEqual(middles.get(3),p))
            return middles.get(0);
        for(int i=0;i<3;i++)
            if(checkEqual(middles.get(i),p))
                return middles.get(i+1);
        return null;
    }
    //This func, manipulate to calulate 4 moves from 1 failed move and update it onto file
    void UpdateFailedMoves(ArrayList<position> computerMoves, File file)throws IOException
  {
      int l=computerMoves.size();
      position[] p1=new position[l];
      position[] p2=new position[l];
      position[] p3=new position[l];
      position[] p4=new position[l];

    for(int i=0;i<l;i++)
    {
        position p=computerMoves.get(i);
        p1[i]=p;
        p2[i]=NextRelative_position(p1[i]);
        p3[i]=NextRelative_position(p2[i]);
        p4[i]=NextRelative_position(p3[i]);
    }
      UpdateOnto_File(p1,file);
      UpdateOnto_File(p2,file);
      UpdateOnto_File(p3,file);
      UpdateOnto_File(p4,file);
  }
//Writing the failed move into file
  void UpdateOnto_File(position[] pos,File file)throws IOException
  {
      StringBuilder str=new StringBuilder();
      for(int i=0;i<pos.length;i++)
          str.append(pos[i].row+","+pos[i].col+" -> ");
      str.append("*");
      try {
          FileWriter writer = new FileWriter(file, true);
          writer.write(str.toString());
          writer.write("\r\n");   // write new line
          writer.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
    //This function return set of moves not to make from its past experience
    HashSet<position> failingMoves(ArrayList<position> moves, File file)throws IOException
    {
        HashSet<position> res=new HashSet<>();
        if(moves.size()==0)
            return res;
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
          while ((line = br.readLine()) != null)
            {
                String[] values=line.split("->");
                int i=0;
                for(int move=0;move<moves.size();move++)
                {
                    String value=values[i].trim();
                    int x=Integer.parseInt(value.split(",")[0]);
                    int y=Integer.parseInt(value.split(",")[1]);
                    if(x!=moves.get(move).row||y!=moves.get(move).col)
                        break;
                    i++;
                    if(move==moves.size())
                    {
                        if(move>=values.length)
                            break;
                        value=values[i+1].trim();
                        if(value.equals("*"))
                            break;
                         x=Integer.parseInt(value.split(",")[0]);
                         y=Integer.parseInt(value.split(",")[1]);
                        res.add(new position(x,y));
                    }
                }
            }
        }
        return res;
    }
}
