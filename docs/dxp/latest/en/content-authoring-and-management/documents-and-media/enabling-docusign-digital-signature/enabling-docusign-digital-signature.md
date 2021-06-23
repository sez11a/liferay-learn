# Enabling DocuSign Digital Signatures

You can now integrate [*DocuSign*](https://www.docusign.com/) digital signatures into your Liferay documents. DocuSign is a service that manages documents to be signed electronically. With this integration, you can manage and collect signatures on your documents.

Before you enable digital signatures in Liferay, make sure you've generated and retrieved your User ID key, API Account key, Account Base URI, Integration key, and RSA Private key. Instructions for doing this can be found on [DocuSign's website](https://support.docusign.com/en/guides/ndse-admin-guide-api-and-keys). 

## Enabling Digital Signatures

1. Open the Global Menu(![Global Menu](../../../images/icon-applications-menu.png)).

1. Click *Control Panel* &rarr; *Instance Settings* &rarr; *Digital Signature*. 

1. Switch the *toggle* to Enable.

    ![Switch the toggle.](./images/01.png)

1. Choose a *Site Settings Strategy* (see below).

    ![Your Site Settings Strategy defines the scope of your digital signatures.](./images/02.png)

1. Click *Save*. 

You have three options for your Site settings strategy: 

**Always Inherit:** All sites are linked to these settings.

**Always Override:** Every site must provide its own configuration.

**Inherit or Override:** Can be defined in both site settings and instance settings. If defined in both, the site settings will override those of the instance.

You must now add your digital keys from DocuSign at the proper scope in Liferay. 

## Adding Digital Keys

Depending on what you chose for your Site Settings strategy, you must add your digital keys at the proper scope: 

- If you chose Always Inherit, add the keys in Instance Settings. 
- If you chose Always Override, add the keys in Site Settings. 
- If you chose Inherit or Override, add the keys in either place.

1. Navigate to _Control Panel_ &rarr; _Instance Settings_ &rarr; _Digital Signature_ or for Site Settings, the _Site Menu_ &rarr; _Configuration_ &rarr; _Site Settings_ &rarr; _Digital Signature_. 

1. If it's not switched already, switch the toggle to _Enabled_.

![Switch the toggle.](./images/03.png)

1. Enter the User ID, API Account ID, Account's Base URI, Integration Key, and the RSA Private Key you previously retrieved from the DocuSign website. 

1. Click *Save* to enable digital signatures. 

## Collecting Document Signatures in Documents and Media

1. For one document, find the document that should be signed and click *Actions* &rarr; *Collect Digital Signature*.![Actions button](./images/05.png) 
1. For multiple documents, select the documents that should be signed and click *Collect Digital Signature* 

    ![You can see multiple selected files and the Collect Digital Signature button.](./images/05.png)

1. Fill the *Envelope*'s information and click on *Send*. 

    ![Fill the envelope's information with its name, recipient's name and email, email's subject and message.](./images/06.png)
    <!--Changing this screenshot to one filled in is in our list, since we'll have a meeting to retake all the screenshots with devs we'll replace this one then.-->

1. when the envelope is sent, Recipients must go through [DocuSign's process](https://www.docusign.com/products/electronic-signature) to sign the document. 
1. When the envelope is sent, the recipient gets an email with a link to the document. Recipients must go through DocuSign's process to sign the document. 

   ![Switch the toggle.](./images/08.png)

   Your Digital Signature is automatically generated based on your full name and the initials you provide. 

1. The copy of the signed document goes back to you and the recipients. 

## Tracking Envelope's Status

DocuSign uses the term _envelope_ to denote a document or collection of documents to be signed. Once sent, you can track your envelope status from within Liferay. 

> You can check the different labels of status on [DocuSign's website](https://support.docusign.com/en/guides/ndse-user-guide-document-status). 

1. Open the *Site Menu* (![Site Menu](../../../images/icon-menu.png)) &rarr; _Content & Data_ &rarr; _Digital Signature_ to see a list of created envelopes. 

    ![You can see when envelopes were created, to whom they were sent, and their status.](./images/07.png)

1. You can also create an envelope directly from this screen using the ![Add Button](../../../images/icon-add.png). You'll be brought to the envelope's screen to enter its information. 

1. Use *Filter and Order* or type keywords in the *Search* bar to find documents.

    ![Find documents through Filter and Order, or Search Bar.](./images/08.png)

1. Click the name of the envelope to see its details, and download the document by clicking the *Download* button. 

    ![You can view the details of your envelopes from inside Liferay.](./images/09.png)

