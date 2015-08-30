package nobugs.team.shopping.repo.mapper;

/**
 * Created by Administrator on 2015/8/23 0023.
 */
public interface IModelMapper<Model, Po> {
    Po fromModel(Model model);
    Model toModel(Po po);
}
