package com.abdullahcanakci;

import java.util.List;


public class PrimeRunner implements Runnable {
  List<Node> list;
  Node result = new Node(1);
  long runtime = 0;

  public PrimeRunner(List<Node> list){
    this.list = list;
  }

  @Override
  public void run() {
    long startTime = System.nanoTime();
    for (int i = 0; i < list.size(); i++) {
      Node temp = list.get(i);
      if(temp.isPrime()){
        if(result.compare(temp) < 0){
          result = temp;
        }
      }
    }
    this.runtime = (System.nanoTime() - startTime) / 1000000;
  }

  public Node getResult() {
    return this.result;
  }

}