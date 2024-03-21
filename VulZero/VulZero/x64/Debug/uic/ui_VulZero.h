/********************************************************************************
** Form generated from reading UI file 'VulZero.ui'
**
** Created by: Qt User Interface Compiler version 6.6.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_VULZERO_H
#define UI_VULZERO_H

#include <QtCore/QVariant>
#include <QtGui/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenu>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_VulZeroClass
{
public:
    QAction *actionOverview_Statistics;
    QAction *actionAlerts_and_Notifications;
    QAction *actionFirewall_Rules;
    QAction *actionIDS_IPS_Configuration;
    QAction *actionDetailed_Logs;
    QAction *actionSearch_and_Filter;
    QAction *actionVisualize_Network_Topology;
    QAction *actionExit;
    QAction *actionNetwork_Map;
    QAction *actionAutomated_Response;
    QAction *actionUpdate_Software;
    QAction *actionHelp;
    QWidget *centralWidget;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;
    QMenuBar *menuBar;
    QMenu *menuDashboard;
    QMenu *menuScans;
    QMenu *menuAnalysis;
    QMenu *menuWorkflow;

    void setupUi(QMainWindow *VulZeroClass)
    {
        if (VulZeroClass->objectName().isEmpty())
            VulZeroClass->setObjectName("VulZeroClass");
        VulZeroClass->resize(716, 497);
        actionOverview_Statistics = new QAction(VulZeroClass);
        actionOverview_Statistics->setObjectName("actionOverview_Statistics");
        actionAlerts_and_Notifications = new QAction(VulZeroClass);
        actionAlerts_and_Notifications->setObjectName("actionAlerts_and_Notifications");
        actionFirewall_Rules = new QAction(VulZeroClass);
        actionFirewall_Rules->setObjectName("actionFirewall_Rules");
        actionIDS_IPS_Configuration = new QAction(VulZeroClass);
        actionIDS_IPS_Configuration->setObjectName("actionIDS_IPS_Configuration");
        actionDetailed_Logs = new QAction(VulZeroClass);
        actionDetailed_Logs->setObjectName("actionDetailed_Logs");
        actionSearch_and_Filter = new QAction(VulZeroClass);
        actionSearch_and_Filter->setObjectName("actionSearch_and_Filter");
        actionVisualize_Network_Topology = new QAction(VulZeroClass);
        actionVisualize_Network_Topology->setObjectName("actionVisualize_Network_Topology");
        actionExit = new QAction(VulZeroClass);
        actionExit->setObjectName("actionExit");
        actionNetwork_Map = new QAction(VulZeroClass);
        actionNetwork_Map->setObjectName("actionNetwork_Map");
        actionAutomated_Response = new QAction(VulZeroClass);
        actionAutomated_Response->setObjectName("actionAutomated_Response");
        actionUpdate_Software = new QAction(VulZeroClass);
        actionUpdate_Software->setObjectName("actionUpdate_Software");
        actionHelp = new QAction(VulZeroClass);
        actionHelp->setObjectName("actionHelp");
        centralWidget = new QWidget(VulZeroClass);
        centralWidget->setObjectName("centralWidget");
        VulZeroClass->setCentralWidget(centralWidget);
        mainToolBar = new QToolBar(VulZeroClass);
        mainToolBar->setObjectName("mainToolBar");
        VulZeroClass->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(VulZeroClass);
        statusBar->setObjectName("statusBar");
        VulZeroClass->setStatusBar(statusBar);
        menuBar = new QMenuBar(VulZeroClass);
        menuBar->setObjectName("menuBar");
        menuBar->setGeometry(QRect(0, 0, 716, 22));
        menuDashboard = new QMenu(menuBar);
        menuDashboard->setObjectName("menuDashboard");
        menuScans = new QMenu(menuBar);
        menuScans->setObjectName("menuScans");
        menuAnalysis = new QMenu(menuBar);
        menuAnalysis->setObjectName("menuAnalysis");
        menuWorkflow = new QMenu(menuBar);
        menuWorkflow->setObjectName("menuWorkflow");
        VulZeroClass->setMenuBar(menuBar);

        menuBar->addAction(menuWorkflow->menuAction());
        menuBar->addAction(menuDashboard->menuAction());
        menuBar->addAction(menuScans->menuAction());
        menuBar->addAction(menuAnalysis->menuAction());
        menuDashboard->addAction(actionOverview_Statistics);
        menuDashboard->addAction(actionAlerts_and_Notifications);
        menuDashboard->addAction(actionNetwork_Map);
        menuScans->addAction(actionFirewall_Rules);
        menuScans->addAction(actionIDS_IPS_Configuration);
        menuScans->addAction(actionAutomated_Response);
        menuAnalysis->addAction(actionDetailed_Logs);
        menuAnalysis->addAction(actionSearch_and_Filter);
        menuAnalysis->addAction(actionUpdate_Software);
        menuAnalysis->addAction(actionHelp);
        menuWorkflow->addAction(actionExit);

        retranslateUi(VulZeroClass);

        QMetaObject::connectSlotsByName(VulZeroClass);
    } // setupUi

    void retranslateUi(QMainWindow *VulZeroClass)
    {
        VulZeroClass->setWindowTitle(QCoreApplication::translate("VulZeroClass", "VulZero", nullptr));
        actionOverview_Statistics->setText(QCoreApplication::translate("VulZeroClass", "Dashboard", nullptr));
        actionAlerts_and_Notifications->setText(QCoreApplication::translate("VulZeroClass", "Logs", nullptr));
        actionFirewall_Rules->setText(QCoreApplication::translate("VulZeroClass", "Firewall Rules", nullptr));
        actionIDS_IPS_Configuration->setText(QCoreApplication::translate("VulZeroClass", "IDS/IPS Configuration", nullptr));
        actionDetailed_Logs->setText(QCoreApplication::translate("VulZeroClass", "User Management", nullptr));
        actionSearch_and_Filter->setText(QCoreApplication::translate("VulZeroClass", "System Settings", nullptr));
        actionVisualize_Network_Topology->setText(QCoreApplication::translate("VulZeroClass", "Visualize Network Topology", nullptr));
        actionExit->setText(QCoreApplication::translate("VulZeroClass", "Exit", nullptr));
        actionNetwork_Map->setText(QCoreApplication::translate("VulZeroClass", "Network Map", nullptr));
        actionAutomated_Response->setText(QCoreApplication::translate("VulZeroClass", "Automated Response", nullptr));
        actionUpdate_Software->setText(QCoreApplication::translate("VulZeroClass", "Update Software", nullptr));
        actionHelp->setText(QCoreApplication::translate("VulZeroClass", "Help", nullptr));
        menuDashboard->setTitle(QCoreApplication::translate("VulZeroClass", "View", nullptr));
        menuScans->setTitle(QCoreApplication::translate("VulZeroClass", "Configuration", nullptr));
        menuAnalysis->setTitle(QCoreApplication::translate("VulZeroClass", "Tools", nullptr));
        menuWorkflow->setTitle(QCoreApplication::translate("VulZeroClass", "File", nullptr));
    } // retranslateUi

};

namespace Ui {
    class VulZeroClass: public Ui_VulZeroClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_VULZERO_H
