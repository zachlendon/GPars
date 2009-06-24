package org.gparallelizer.samples.dataflow

import org.gparallelizer.dataflow.DataFlowActor
import org.gparallelizer.dataflow.DataFlowVariable
import static org.gparallelizer.dataflow.DataFlow.thread

/**
 * An example showing multiple threads calculating different parts of a complex physical calculation
 * and one thread consolidating the results of individual calculations into a final report.
 */

final def mass = new DataFlowVariable()
final def radius = new DataFlowVariable()
final def volume = new DataFlowVariable()
final def density = new DataFlowVariable()
final def acceleration = new DataFlowVariable()
final def time = new DataFlowVariable()
final def velocity = new DataFlowVariable()
final def decelerationForce = new DataFlowVariable()
final def deceleration = new DataFlowVariable()
final def distance = new DataFlowVariable()
final def author = new DataFlowVariable()

thread {
    println """
Calculating distance required to stop a moving ball.
====================================================
The ball has a radius of ${radius.val} meters and is made of a material with ${density.val} kg/m3 density,
which means that the ball has a volume of ${volume.val} m3 and a mass of ${mass.val} kg.
The ball has been accelerating with ${acceleration.val} m/s2 from 0 for ${time.val} seconds and so reached a velocity of ${velocity.val} m/s.

Given our ability to push the ball backwards with a force of ${decelerationForce.val} N (Newton), we can cause a deceleration
of ${deceleration.val} m/s2 and so stop the ball at a distance of ${distance.val} m.

=======================================================================================================================
This example has been calculated asynchronously in multiple threads using GParallelizer DataFlow concurrency in Groovy.
Author: ${author.val}
"""

    System.exit 0
}

thread {
    mass << volume.val * density.val
}

thread {
    volume << Math.PI * (radius.val ** 3)
}

thread {
    radius << 2.5
    density << 	998.2071  //water
    acceleration << 9.80665 //free fall
    decelerationForce << 900
}

thread {
    println 'Enter your name:'
    def name = new InputStreamReader(System.in).readLine()
    author << (name?.trim()?.size()>0 ? name : 'anonymous')
}

thread {
    time << 10
    velocity << acceleration.val * time.val
}

thread {
    deceleration << decelerationForce.val / mass.val
}

thread {
    distance << deceleration.val * ((velocity.val/deceleration.val) ** 2) * 0.5
}

Thread.sleep(30000)