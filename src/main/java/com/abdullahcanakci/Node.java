package com.abdullahcanakci;

public class Node {
  public int value;

  public Node(int i){
    this.value = i;
  }

  public boolean isPrime(){

    //AKS primality test.

    int n = this.value;
    if(n <= 1) return false;
    if(n <= 3) return true;
    if(n % 2 == 0|| n % 3 == 0) return false;
    for(int i = 5; i * i <= n; i+=6){
      if(n % i == 0 || n % (i + 2) == 0){
        return false;
      }
    }
    return true;
  }

  public int compare(Node other) {
    return Integer.compare(this.value, other.value);
  }
}