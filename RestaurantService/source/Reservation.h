#ifndef RESTAURANTSERVICE_RESERVATION_H
#define RESTAURANTSERVICE_RESERVATION_H

#include <string>
#include <vector>

#include "Table.h"

class Reservation {
    enum ReservationStatus { READY, DENIED, PENDING };
public:
    Reservation() {};

    int id() const { return mId; };

    void setId(int id) { mId = id; };

    Table table() const { return mTable; };

    void setTable(Table table) { mTable = table; };

    std::string time() const { return mTime; };

    void setTime(std::string time) { mTime = time; };

    ReservationStatus reservationStatus() const { return mStatus; };

    void setStatus(ReservationStatus status) { mStatus = status; };

    int persons() const { return mPersons; };

    void setPersons(int persons) { mPersons = persons; };

private:
    int mId;

    Table mTable;

    std::string mTime;

    ReservationStatus mStatus;

    int mPersons;
};

#endif //RESTAURANTSERVICE_RESERVATION_H
