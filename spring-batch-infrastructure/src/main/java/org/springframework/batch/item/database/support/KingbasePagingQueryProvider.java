package org.springframework.batch.item.database.support;

import org.springframework.util.StringUtils;

public class KingbasePagingQueryProvider extends AbstractSqlPagingQueryProvider {

    @Override
    public String generateFirstPageQuery(int pageSize) {
        return SqlPagingQueryUtils.generateLimitSqlQuery(this, false, buildLimitClause(pageSize));
    }

    @Override
    public String generateRemainingPagesQuery(int pageSize) {
        if(StringUtils.hasText(getGroupClause())) {
            return SqlPagingQueryUtils.generateLimitGroupedSqlQuery(this, true, buildLimitClause(pageSize));
        }
        else {
            return SqlPagingQueryUtils.generateLimitSqlQuery(this, true, buildLimitClause(pageSize));
        }
    }

    private String buildLimitClause(int pageSize) {
        return new StringBuilder().append("LIMIT ").append(pageSize).toString();
    }

    @Override
    public String generateJumpToItemQuery(int itemIndex, int pageSize) {
        int page = itemIndex / pageSize;
        int offset = (page * pageSize) - 1;
        offset = offset<0 ? 0 : offset;
        String limitClause = new StringBuilder().append("LIMIT 1 OFFSET ").append(offset).toString();
        return SqlPagingQueryUtils.generateLimitJumpToQuery(this, limitClause);
    }

}