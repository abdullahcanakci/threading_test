package com.abdullahcanakci;

import java.util.List;

public class EqualityRunner implements Runnable {
  List<Node> list;
  Node result;
  long runtime = 0;

  public EqualityRunner(List<Node> list){
    this.list = list;
  }

  @Override
  public void run() {
    long startTime = System.nanoTime();
    result = list.get(0);
    for (int i = 0; i < list.size(); i++) {
      if(result.compare(list.get(i)) < 0){
        result = list.get(i);
      }
    }
    this.runtime = (System.nanoTime() - startTime) / 1000000;
  }

  public Node getResult() {
    return this.result;
  }
}