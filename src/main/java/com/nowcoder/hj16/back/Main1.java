package com.nowcoder.hj16.back;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main1 {
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int money = sc.nextInt();
        int n = sc.nextInt();
        if(n<=0||money<=0) System.out.println(0);
 
        Good[] goods = new Good[n+1];
        for (int i = 1; i <= n; i++) {
            int v = sc.nextInt();//价格
            int p = sc.nextInt();//重要度
            int q = sc.nextInt();//如果 q>0 ，表示该物品为附件， q 是所属主件的编号
            goods[i] = new Good(i,v,p,q);
 
        }
        
		/*
		 * Arrays.sort(goods, new Comparator<Good>() {
		 * 
		 * @Override public int compare(Good o1, Good o2) { if(o1==null||o2==null) {
		 * return 1; } return o2.getSatisfy()-o1.getSatisfy(); }});
		 */
        
       
        Map<Integer,List<Good>> paths=new HashMap<Integer,List<Good>>();
        //pathid, price , satisfy
        Integer[][] price_sum_satisfy=new Integer[n+1][3];
        boolean[] cannot_selected=new boolean[n+1];
       
        int max_satisfy=0;
        for (int i = 1; i <= n; i++) {
        	
        	
        	if(!paths.containsKey(i)) {
        		paths.put(i, new ArrayList<Good>());        		
        	}
        	price_sum_satisfy[i][0]= i;price_sum_satisfy[i][1]= 0;price_sum_satisfy[i][2]= 0;
        	if(goods[i].isPart&&cannot_selected[i]==true) {//是配件，会超过价格总和，永远不能选
    			continue;
    		}
    		int sum_price=price_sum_satisfy[i][1]+goods[i].getV();
    		if(sum_price<=money) {//价格总和
    			price_sum_satisfy[i][1]+=goods[i].getV();
    			price_sum_satisfy[i][2]+=goods[i].getSatisfy();
    			paths.get(i).add(goods[i]);
    		}
    		        		        		
    		if(goods[i].isPart&&cannot_selected[i]==false) {
    			int pid=goods[i].getQ();
    			int sum_price2=price_sum_satisfy[i][1]+goods[pid].getV();
    			
        		if(sum_price2<=money) {//价格总和
        			price_sum_satisfy[i][1]+=goods[pid].getV();
        			price_sum_satisfy[i][2]+=goods[pid].getSatisfy();
        			paths.get(i).add(goods[pid]);
        		}else {
        			//当前是配件，主件和配件都选上了，价格和大于极限money的值，永远不能再选
        			cannot_selected[i]=true;
        		}
    			
    			
    		}
              
        	for(int j=1;j!=i&&j<=n;j++) {
        		
        		
        		if(goods[j].isPart&&cannot_selected[j]==true) {//是配件，会超过价格总和，永远不能选
        			continue;
        		}
        		int sum_price3=price_sum_satisfy[i][1]+goods[j].getV();
        		if(sum_price3<=money) {//价格总和
        			price_sum_satisfy[i][1]+=goods[j].getV();
        			price_sum_satisfy[i][2]+=goods[j].getSatisfy();
        			paths.get(i).add(goods[j]);
        		}
        		        		        		
        		if(goods[j].isPart&&cannot_selected[j]==false) {
        			int pid=goods[j].getQ();
        			int sum_price4=price_sum_satisfy[i][1]+goods[pid].getV();
        			
            		if(sum_price4<=money) {//价格总和
            			price_sum_satisfy[i][1]+=goods[pid].getV();
            			price_sum_satisfy[i][2]+=goods[pid].getSatisfy();
            			paths.get(i).add(goods[pid]);
            		}else {
            			//当前是配件，主件和配件都选上了，价格和大于极限money的值，永远不能再选
            			cannot_selected[j]=true;
            		}
        			
        			
        		}
        		
        	}
           //
        	max_satisfy=Math.max(max_satisfy, price_sum_satisfy[i][2]);
 
        }
        
        System.out.println(max_satisfy);
        
     
 
    }
 
 
   
}
/**
 * 定义物品类
 */
class Good{
	private int id;
   
	private int v;  //物品的价格
	private int p;  //物品的重要度
	private int satisfy;//满意度=v*p
	private int q;  //物品的主附件ID

    boolean isPart=false;//true ，表示是备件，否则是主件
    
    public int getId() {
		return id;
	}
	public int getV() {
		return v;
	}
	public int getP() {
		return p;
	}
	public int getSatisfy() {
		return satisfy;
	}
	public int getQ() {
		return q;
	}
	public boolean isPart() {
		return isPart;
	}
	@Override
	public boolean equals(Object other) {		
		
        if (other == this) {
            return true;
        } 
   
        if (!(other instanceof Good)) {
            return false;
        }         
        // typecast o to Complex so that we can compare data members
        Good c = (Good) other;        
        
        return this.id==c.getId();
		
	}
	
    public Good(int id, int v, int p, int q) {
		super();
		this.id = id;
		this.v = v;
		this.p = p;
		this.satisfy=v*p;
		this.q = q;
		if(this.q>0) {
			this.isPart=true;
		}
	}

  
}