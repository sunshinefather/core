package test.enums;

public enum TrafficLamp {
    RED(30){
        TrafficLamp getNext()
        {
            return GREEN;
        }
    }, 
    GREEN(45)
    {
        TrafficLamp getNext()
        {
            return YELLOW;
        }
        }, 
    YELLOW(5)
    {
        TrafficLamp getNext()
        {
            return RED;
        }
    };

    abstract TrafficLamp getNext();

    int time;
    TrafficLamp(int time)
    {
        this.time = time;
        System.out.println(time);
    }
}
enum MaYun {
  himself,m2; //定义一个枚举的元素，就代表MaYun的一个实例
private String anotherField;
public void splitAlipay() {
System.out.println("Alipay是我的啦！看你丫Yahoo绿眉绿眼的望着。。。");
}
public String setALlAPI(String name){
    anotherField=name;
    return this.name();
}
public void pNM(){
    System.out.println(anotherField);
}
}
