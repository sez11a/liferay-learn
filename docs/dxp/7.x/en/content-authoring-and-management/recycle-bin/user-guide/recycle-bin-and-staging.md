# Recycle Bin and Staging

If you have a *Staging environment* enabled for your Site, you will have a separate Recycle Bin for both the *Staging environment* and the *Live environment*. This prevents staged assets and live assets from mixing. 

To move an asset to the Recycle Bin, first switch to the environment that the assets exists on. Click on Staging or Live. 

![Check which environment you are working in. Click on Staging or Live to switch.](recycle-bin-and-staging/images/01.png)

[Using the Recycle Bin](using-the-recycle-bin.md) has the same functionality whether you are working in a *Staging environment* or a *Live environment*. 

Note that the Recycle Bin is enabled for different asset types when the Staging environment is initially configured. See [Managing Data and Content Types in Staging](../../../site-building/publishing-tools/staging/managing-data-and-content-types-in-staging.md) to make any changes.

<!-- Not completely sure about these warnings. I tried enabled and disabling the staging environment and am describing my experience -->

```warning::
   Check your Recycle Bin before enabling a Staging environment. You may end up losing assets that are already in the Recycle Bin. 
```

```warning::
   Likewise, check your Staging environment's Recycle Bin before disabling the Staging environment. You may end up losing assets after disabling.
```