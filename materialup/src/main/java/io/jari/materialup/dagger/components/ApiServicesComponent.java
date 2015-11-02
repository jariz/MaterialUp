package io.jari.materialup.dagger.components;


import dagger.Component;
import io.jari.materialup.api.PostsService;
import io.jari.materialup.dagger.modules.ApiServicesModule;
import io.jari.materialup.dagger.modules.RetrofitModule;
import io.jari.materialup.dagger.scopes.PerApp;
import io.jari.materialup.ui.activities.ItemActivity;
import io.jari.materialup.ui.fragments.CategoryFragment;

/**
 * Created by Akash.
 */
@PerApp
@Component(
        dependencies = MaterialUpComponent.class,
        modules = {
                ApiServicesModule.class,
                RetrofitModule.class
        }
)
public interface ApiServicesComponent {

    void inject(CategoryFragment fragment);

    void inject(ItemActivity activity);

    PostsService getPostsService();
}
