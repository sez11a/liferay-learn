# Using the Autofill Rule

The _Autofill_ Rule filters a form field's options based on pre-configured conditions. If you already have a [data provider](../data-providers/data-providers-overview.md) enabled, the _Autofill_ Rule is the next step in enhancing your form. See [Using Data Providers to Populate Form Options](../data-providers/using-the-rest-data-provider-to-populate-form-options.md) to learn more.

The sample form below asks the respondent to select either a cash reward or a vacation. If the respondent selects the latter, she must then choose the geographical region and then a country. The form uses an Autofill Rule to filter countries based on a geographic region.

Follow the steps below:

1. Configure a [data provider which imports the countries of the world with regional filters.](../data-providers/using-the-rest-data-provider-to-populate-form-options.md)
1. [Create a form](../creating-and-managing-forms/creating-forms.md) with these fields: 

    * A Single Select field called _Rewards_ with two options: *Cash* or *All expenses paid trip*
    * A Text field called _Region_
    * A Select from List field called _Choose a Destination Country_ that uses the [restcountries.eu](https://restcountries.eu) data provider

## Configuring the Autofill Rule

1. Click the _Rules_ tab.

1. Click the *Add* (![Add](../../../images/icon-add.png)) button.

1. Select _Reward_ from the If Condition.

1. Create the rule: _Is equal to_ &rarr; _Value_ &rarr; _All expenses paid trip_.

1. Select _Autofill_ from the _Do_ Action selector. 

1. Select the data provider from the _From Data Provider_ selector. 

1. Select _Region_ from the _Region_ selector. 

1. Select _Country_ from the _Country_ selector. 

    ![Create the Autofill rule.](./using-the-autofill-rule/images/01.png)

1. Click _Save_ when finished.

## Verifying the Autofill Rule

1. Publish the form.

1. Navigate to the Site where the form is displayed.

1. Enter a valid region into the Region field and observe that the options in the Select from List Field are filtered based on the Region. The [restcountries.eu](https://restcountries.eu) service has these regions: Africa, Americas, Asia, Europe, Oceania, and Polar.

    ![Filter countries by region of the world.](./using-the-autofill-rule/images/02.gif)

Users can now search for a region then the countries in the form.

## Additional Information

* [Creating Forms](../creating-and-managing-forms/creating-forms.md)
* [Data Providers Overview](../data-providers/data-providers-overview.md)
* [Using Data Providers to Populate Form Options](../data-providers/using-the-rest-data-provider-to-populate-form-options.md)
