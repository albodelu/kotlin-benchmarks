package org.jetbrains

import org.openjdk.jmh.annotations.*
import java.util.concurrent.*
import org.openjdk.jmh.infra.Blackhole

State(Scope.Thread)
BenchmarkMode(Mode.Throughput)
OutputTimeUnit(TimeUnit.SECONDS)
Warmup(iterations = 5)
Measurement(iterations = 5)
Fork(2)
open class IntArrayBenchmark {
    var data: IntArray
    {
        val list = IntArray(SIZE)
        var index = 0
        for (n in intValues())
            list[index++] = n
        data = list
    }

    Benchmark fun countFilteredManual(bh: Blackhole) {
        var count = 0
        for (it in data) {
            if (it % 2 == 0)
                count++
        }
        bh.consume(count)
    }

    Benchmark fun filterAndCount(bh: Blackhole) {
        bh.consume(data.filter { it % 2 == 0 }.count())
    }

    Benchmark fun filterAndCountWithValue(bh: Blackhole) {
        val value = data.filter { it % 2 == 0 }.count()
        bh.consume(value)
    }

    Benchmark fun filterAndMap(bh: Blackhole) {
        for (item in data.filter { it % 2 == 0 }.map { it * 10 })
            bh.consume(item)
    }

    Benchmark fun filter(bh: Blackhole) {
        for (item in data.filter { it % 2 == 0 })
            bh.consume(item)
    }

    Benchmark fun countFiltered(bh: Blackhole) {
        bh.consume(data.count { it % 2 == 0 })
    }

    Benchmark fun countFilteredWithValue(bh: Blackhole) {
        val value = data.count { it % 2 == 0 }
        bh.consume(value)
    }

    Benchmark fun countFilteredLocal(bh: Blackhole) {
        bh.consume(data.cnt { it % 2 == 0 })
    }

    Benchmark fun countFilteredLocalWithValue(bh: Blackhole) {
        val local = data.cnt { it % 2 == 0 }
        bh.consume(local)
    }
}
