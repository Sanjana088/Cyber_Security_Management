#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_VulZero.h"

class VulZero : public QMainWindow
{
    Q_OBJECT

public:
    VulZero(QWidget *parent = nullptr);
    ~VulZero();

private:
    Ui::VulZeroClass ui;
};
