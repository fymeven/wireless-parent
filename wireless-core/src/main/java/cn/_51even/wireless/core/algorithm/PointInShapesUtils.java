package cn._51even.wireless.core.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PointInShapesUtils {

    private static Logger logger = LoggerFactory.getLogger(PointInShapesUtils.class);

    public static boolean isPtInPoly(Location point, List<Location> pts){

        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of lat
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Location p1, p2;//neighbour bound vertices
        Location p = point; //当前点

        p1 = pts.get(0);//left vertex
        for(int i = 1; i <= N; ++i){//check all rays
            if(p.equals(p1)){
                return boundOrVertex;//p is an vertex
            }

            p2 = pts.get(i % N);//right vertex
            if(p.lat < Math.min(p1.lat, p2.lat) || p.lat > Math.max(p1.lat, p2.lat)){//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if(p.lat > Math.min(p1.lat, p2.lat) && p.lat < Math.max(p1.lat, p2.lat)){//ray is crossing over by the algorithm (common part of)
                if(p.lon <= Math.max(p1.lon, p2.lon)){//lat is before of ray
                    if(p1.lat == p2.lat && p.lon >= Math.min(p1.lon, p2.lon)){//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if(p1.lon == p2.lon){//ray is vertical
                        if(p1.lon == p.lon){//overlies on a vertical ray
                            return boundOrVertex;
                        }else{//before ray
                            ++intersectCount;
                        }
                    }else{//cross point on the left side
                        double xinters = (p.lat - p1.lat) * (p2.lon - p1.lon) / (p2.lat - p1.lat) + p1.lon;//cross point of lon
                        if(Math.abs(p.lon - xinters) < precision){//overlies on a ray
                            return boundOrVertex;
                        }

                        if(p.lon < xinters){//before ray
                            ++intersectCount;
                        }
                    }
                }
            }else{//special case when ray is crossing through the vertex
                if(p.lat == p2.lat && p.lon <= p2.lon){//p crossing over p2
                    Location p3 = pts.get((i+1) % N); //next vertex
                    if(p.lat >= Math.min(p1.lat, p3.lat) && p.lat <= Math.max(p1.lat, p3.lat)){//p.lat lies between p1.lat & p3.lat
                        ++intersectCount;
                    }else{
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if(intersectCount % 2 == 0){//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }

    }

    /**
     * 判断是否在圆形内
     * @param p
     * @param c
     * @return
     */
    public static String distencePC(Location p, Circle c){//判断点与圆心之间的距离和圆半径的关系
        String s ;
        double d2 = Math.hypot( (p.getLat() - c.getLocation().getLat() ), (p.getLon() - c.getLocation().getLon()) );
        logger.debug("d2=="+d2);
        double r = c.getRadius();
        if(d2 > r){
            s = "圆外";
        }else if(d2 < r){
            s = "圆内";
        }else{
            s = "圆上";
        }
        return s;
    }




    public static void main(String[] args) {

        Location point = new Location(116.404072, 39.916605);

        // 测试一个点是否在多边形内
        List<Location> pts = new ArrayList<Location>();
        pts.add(new Location(116.395, 39.910));
        pts.add(new Location(116.394, 39.914));
        pts.add(new Location(116.403, 39.920));
        pts.add(new Location(116.402, 39.914));
        pts.add(new Location(116.410, 39.913));

        if(isPtInPoly(point, pts)){
            logger.debug("点在多边形内");
        }else{
            logger.debug("点在多边形外");
        }

        // 测试一个点是否在圆形内
        Location centerPoint = new Location(116.404172, 39.916605);
        Circle c = new Circle();
        c.setLocation(centerPoint);
        c.setRadius(0.0056);
        String s = distencePC(point,c);
        logger.debug("点是否在圆内："+s);
    }
}
