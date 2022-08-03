package com.ynz.demo.asynccomputing;

import java.util.Random;
import java.util.concurrent.Callable;

public class PrintNumberTask implements Callable<Integer> {

  @Override
  public Integer call() {
    return new Random().nextInt(100);
  }
}
