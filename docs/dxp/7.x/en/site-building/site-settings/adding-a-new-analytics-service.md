# Adding a New Analytics Service

Liferay DXP includes support for Google Analytics and Piwik for analyzing traffic on your Site. If you require a different analytics service, you can add it for your Site. Follow these steps to enable a new analytics service:

## Adding a New Analytics Service

1. Open the Product Menu and go to *Control Panel* &rarr; *Configuration* &rarr; *Instance Settings*.
1. Select *Analytics* under the Platform heading.
1. Enter the name of any additional service you want to add in the *Analytics* field provided.

    ![You can enter additional analytics services through Instance Settings.](./adding-a-new-analytics-service/images/01.png)

## Adding the Tracking Code for your Analytics Service

1. Once you have entered the name, open the Product Menu and go to the *Configuration* &rarr; *Site Settings* &rarr; *Advanced* &rarr; *Analytics* page for the Site where you wish to add analytics.
1. Copy the JavaScript tracking code provided by your analytics platform into the corresponding field for your service.

    ![The new analytics service appears under the Site's advanced configuration settings.](./adding-a-new-analytics-service/images/02.png)

Now all pages on the selected Site contain the tracking script and send analytics data to your analytics platform.
