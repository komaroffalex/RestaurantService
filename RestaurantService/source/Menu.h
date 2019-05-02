#ifndef RESTAURANTSERVICE_MENU_H
#define RESTAURANTSERVICE_MENU_H

#include <string>
#include <vector>

class Menu {    
public:
    Menu() {};

    std::vector<std::string> courses() const { return mCourses; };

    void setCourses(std::vector<std::string> courses) { mCourses = courses; };

    std::vector<std::string> prices() const { return mPrices; };

    void setPrices(std::vector<std::string> prices) { mPrices = prices; };

    std::vector<float> calories() const { return mCalories; };

    void setCalories(std::vector<float> calories) { mCalories = calories; };

    std::vector<float> weight() const { return mWeight; };

    void setWeight(std::vector<float> weight) { mWeight = weight; };

    std::vector<std::string> contents() const { return mContents; };

    void setContents(std::vector<std::string> contents) { mContents = contents; };

private:
    std::vector<std::string> mCourses;

    std::vector<std::string> mPrices;

    std::vector<float> mCalories;

    std::vector<float> mWeight;

    std::vector<std::string> mContents;
};

#endif //RESTAURANTSERVICE_MENU_H
