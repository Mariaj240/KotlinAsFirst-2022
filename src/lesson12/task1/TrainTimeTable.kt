@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import ru.spbstu.kotlin.typeclass.classes.Monoid.Companion.plus

/**
 * Класс "расписание поездов".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит расписание поездов для определённой станции отправления.
 * Для каждого поезда хранится конечная станция и список промежуточных.
 * Поддерживаемые методы:
 * добавить новый поезд, удалить поезд,
 * добавить / удалить промежуточную станцию существующему поезду,
 * поиск поездов по времени.
 *
 * В конструктор передаётся название станции отправления для данного расписания.
 */
class TrainTimeTable(val baseStationName: String) {
    /**
     * Добавить новый поезд.
     *
     * Если поезд с таким именем уже есть, следует вернуть false и ничего не изменять в таблице
     *
     * @param train название поезда
     * @param depart время отправления с baseStationName
     * @param destination конечная станция
     * @return true, если поезд успешно добавлен, false, если такой поезд уже есть
     */
    val listOfTrains = mutableListOf<Train>()
    fun addTrain(train: String, depart: Time, destination: Stop): Boolean {
        var baseStation = Stop(baseStationName, depart)
        for (i in listOfTrains) {
            if (i.name == train) {
                return false
            }
        }
        var stopsList = mutableListOf<Stop>()
        stopsList.add(baseStation)
        stopsList.add(destination)
        var newTrain: Train = Train(train, stopsList)
        listOfTrains.add(newTrain)

        return true
    }

    /**
     * Удалить существующий поезд.
     *
     * Если поезда с таким именем нет, следует вернуть false и ничего не изменять в таблице
     *
     * @param train название поезда
     * @return true, если поезд успешно удалён, false, если такой поезд не существует
     */
    fun removeTrain(train: String): Boolean {
        for (i in listOfTrains) {
            if (i.name == train) {
                listOfTrains.remove(i)
                return true
            }
        }
        return false
    }

    /**
     * Добавить/изменить начальную, промежуточную или конечную остановку поезду.
     *
     * Если у поезда ещё нет остановки с названием stop, добавить её и вернуть true.
     * Если stop.name совпадает с baseStationName, изменить время отправления с этой станции и вернуть false.
     * Если stop совпадает с destination данного поезда, изменить время прибытия на неё и вернуть false.
     * Если stop совпадает с одной из промежуточных остановок, изменить время прибытия на неё и вернуть false.
     *
     * Функция должна сохранять инвариант: время прибытия на любую из промежуточных станций
     * должно находиться в интервале между временем отправления с baseStation и временем прибытия в destination,
     * иначе следует бросить исключение IllegalArgumentException.
     * Также, время прибытия на любую из промежуточных станций не должно совпадать с временем прибытия на другую
     * станцию или с временем отправления с baseStation, иначе бросить то же исключение.
     *
     * @param train название поезда
     * @param stop начальная, промежуточная или конечная станция
     * @return true, если поезду была добавлена новая остановка, false, если было изменено время остановки на старой
     */
    fun addStop(train: String, stop: Stop): Boolean {
        for (trainInList in listOfTrains) {
            if (train == trainInList.name) {

                if (stop.name != baseStationName && (stop.time.compareTo(trainInList.stops[0].time) == -1 || stop.time.compareTo(
                        trainInList.stops[trainInList.stops.size - 1].time
                    ) == 1)
                ) {
                    throw IllegalArgumentException()
                }

                for (index in trainInList.stops.indices) {
                    var currentStop = trainInList.stops[index]
                    var nextIndex = index + 1
                    if (index == trainInList.stops.size - 1) {
                        nextIndex = trainInList.stops.size - 1
                    }
                    if ((stop.name == currentStop.name && trainInList.stops.size > 1 && stop.time.compareTo(trainInList.stops[nextIndex].time) == 1)
                        || (stop.name == currentStop.name && index >= 1 && stop.time.compareTo(trainInList.stops[index - 1].time) == -1)
                    ) {
                        throw IllegalArgumentException()
                    } else if (stop.name == currentStop.name && trainInList.stops.size > 1 && stop.time.compareTo(
                            trainInList.stops[nextIndex].time
                        ) == -1
                    ) {
                        var previousStop = stop.name
                        this.removeStop(train, currentStop.name)
                        var newTime = stop.time
                        var newStop = Stop(previousStop, newTime)
                        var newListOfStops = trainInList.stops.toMutableList()
                        newListOfStops.add(index, newStop)
                        var previousTrain = trainInList.name
                        this.removeTrain(trainInList.name)
                        var newTrain = Train(previousTrain, newListOfStops)
                        listOfTrains.add(newTrain)
                        return false
                    }
                    //for (stopStation in trainInList.stops) {
                    if (stop.name != currentStop.name) {
                        if (stop.name == baseStationName) {
                            var baseStation = baseStationName
                            this.removeStop(train, baseStationName)
                            var newTime = stop.time
                            var newStop = Stop(baseStation, newTime)
                            var newList = trainInList.stops.toMutableList()
                            newList.add(0, newStop)
                            var previousTrain = trainInList.name
                            this.removeTrain(trainInList.name)
                            var newTrain = Train(previousTrain, newList)
                            listOfTrains.add(newTrain)
                            return false
                        }
                        var newStop = Stop(stop.name, stop.time)
                        var newList = trainInList.stops.toMutableList()
                        newList.add(newStop)
                        var previousTrain = trainInList.name
                        this.removeTrain(trainInList.name)
                        var newTrain = Train(previousTrain, newList)
                        listOfTrains.add(newTrain)
                    }
                }
                var result = -1
                var index = 0
                while ((index < trainInList.stops.size) && (stop.time.compareTo(trainInList.stops[index].time) == 1)) {
                    index++
                }
                var listOfStops = trainInList.stops.toMutableList()
                if (index < trainInList.stops.size) {
                    listOfStops.add(index, stop)
                    var previousTrain = trainInList.name
                    this.removeTrain(trainInList.name)
                    var newTrain = Train(previousTrain, listOfStops)
                    listOfTrains.add(newTrain)
                    return true
                } else {
                    listOfStops.add(stop)
                    var previousTrain = trainInList.name
                    this.removeTrain(trainInList.name)
                    var newTrain = Train(previousTrain, listOfStops)
                    listOfTrains.add(newTrain)
                    return true
                }
            }
        }
        return false
    }

    /**
     * Удалить одну из промежуточных остановок.
     *
     * Если stopName совпадает с именем одной из промежуточных остановок, удалить её и вернуть true.
     * Если у поезда нет такой остановки, или stopName совпадает с начальной или конечной остановкой, вернуть false.
     *
     * @param train название поезда
     * @param stopName название промежуточной остановки
     * @return true, если удаление успешно
     */
    fun removeStop(train: String, stopName: String): Boolean {
        for (stopList in listOfTrains) {
            if (stopName == baseStationName || stopList.stops[stopList.stops.size - 1].name == stopName) {
                return false
            }
            for (index in stopList.stops.indices) {
                if (stopList.stops[index].name == stopName) {
                    stopList.stops.minus(stopList.stops[index])
                    return true
                }
            }
        }
        return false
    }

    /**
     * Вернуть список всех поездов, упорядоченный по времени отправления с baseStationName
     */
    fun trains(): List<Train> {
        return listOfTrains.sortedBy { it.stops[0].time }
    }

    /**
     * Вернуть список всех поездов, отправляющихся не ранее currentTime
     * и имеющих остановку (начальную, промежуточную или конечную) на станции destinationName.
     * Список должен быть упорядочен по времени прибытия на станцию destinationName
     */
    fun trains(currentTime: Time, destinationName: String): List<Train> {
        var trainList = mutableListOf<Train>()

        for (trains in listOfTrains) {
            var index = 0
            var newStopsList = mutableListOf<Stop>()
            var v = false
            for (stop in trains.stops) {
                if (stop.name == destinationName) {
                    v = true
                }
            }
            if (v == true) {
                if (trains.stops[0].time.compareTo(currentTime) == 1) {

                    var newTrain = Train(trains.name, trains.stops)
                    trainList.add(newTrain)
                }
            }
        }
        return trainList.sortedBy { it.stops[0].time }.asReversed()
    }

    /**
     * Сравнение на равенство.
     * Расписания считаются одинаковыми, если содержат одинаковый набор поездов,
     * и поезда с тем же именем останавливаются на одинаковых станциях в одинаковое время.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is TrainTimeTable) {
            return false
        } else {
            var others = other as TrainTimeTable
            var numberOfTrains = 0
            var numberOfOtherTrains = 0
            var numberOfStops = 0
            var numberOfOtherStops = 0
            for (trains in listOfTrains) {
                numberOfTrains += 1
                numberOfOtherTrains = 0
                for (otherTrains in others.listOfTrains) {
                    if (trains.name == otherTrains.name) {
                        for (index in trains.stops.indices) {
                            if (otherTrains.stops[index].name != trains.stops[index].name
                                || otherTrains.stops[index].time != trains.stops[index].time
                            ) {
                                return false
                            }
                        }
                    }
                    numberOfOtherStops = otherTrains.stops.size
                    numberOfOtherTrains += 1
                }
                numberOfStops = trains.stops.size
            }
            if (numberOfTrains != numberOfOtherTrains) {
                return false
            }
            if (numberOfStops != numberOfOtherStops) {
                return false
            }
        }
        return true
    }
}

/**
 * Время (часы, минуты)
 */
data class Time(val hour: Int, val minute: Int) : Comparable<Time> {
    /**
     * Сравнение времён на больше/меньше (согласно контракту compareTo)
     */
    override fun compareTo(other: Time): Int {
        if (this.hour > other.hour) return 1
        else if (this.hour == other.hour) {
            if (this.minute == other.minute) return 0
            else if (this.minute > other.minute) return 1
            else if (this.minute < other.minute) return -1
        } else if (this.hour < other.hour) return -1
        return -1
    }
}

/**
 * Остановка (название, время прибытия)
 */
data class Stop(val name: String, var time: Time)

/**
 * Поезд (имя, список остановок, упорядоченный по времени).
 * Первой идёт начальная остановка, последней конечная.
 */
data class Train(val name: String, val stops: List<Stop>) {
    constructor(name: String, vararg stops: Stop) : this(name, stops.asList())
}